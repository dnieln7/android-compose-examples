package xyz.dnieln7.portfolio.data.repository

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotContain
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.data.datasource.LocalProjectDataSource
import xyz.dnieln7.portfolio.data.datasource.RemoteProjectDataSource
import xyz.dnieln7.portfolio.domain.exception.DeleteProjectException
import xyz.dnieln7.portfolio.domain.exception.UpdateProjectException
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.extensions.coVerifyNever
import xyz.dnieln7.portfolio.extensions.coVerifyOnce
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject
import xyz.dnieln7.portfolio.fake.buildSuccessfulResult
import xyz.dnieln7.portfolio.fake.buildUnSuccessfulResult

@ExperimentalCoroutinesApi
class DefaultProjectRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val remoteProjectDataSource = relaxedMockk<RemoteProjectDataSource>()
    private val localProjectDataSource = relaxedMockk<LocalProjectDataSource>()

    private lateinit var repository: DefaultProjectRepository

    @Before
    fun setUp() {
        val dateProvider = DateProvider()

        repository = DefaultProjectRepository(
            dateProvider = dateProvider,
            remoteProjectDataSource = remoteProjectDataSource,
            localProjectDataSource = localProjectDataSource,
        )
    }

    @Test
    fun `GIVEN syncWithRemote equal to false WHEN getProjects THEN getProjects (remote) should never be called and getProjects (local) should be called once`() {
        coEvery { remoteProjectDataSource.getProjects() } returns emptyList<Project>().right()

        runTest(dispatcher) {
            repository.getProjects(false)

            coVerifyNever {
                remoteProjectDataSource.getProjects()
            }

            coVerifyOnce {
                localProjectDataSource.getProjects()
            }
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN getProjects THEN return the expected Exception`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)

        coEvery { remoteProjectDataSource.getProjects() } returns exception.left()

        runTest(dispatcher) {
            val result = repository.getProjects(true).swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo exception
        }
    }

    @Test
    fun `GIVEN local data older than remote data WHEN getProjects THEN return remote data without changes`() {
        val remote1 = buildProject(id = 1, updatedAt = "2021-05-01T00:00:00Z")
        val remote2 = buildProject(id = 2, updatedAt = "2021-05-01T00:00:00Z")
        val remoteData = listOf(remote1, remote2)

        val local1 = buildProject(id = 1, updatedAt = "2021-04-29T00:00:00Z")
        val local2 = buildProject(id = 2, updatedAt = "2021-04-15T00:00:00Z")
        val localData = listOf(local1, local2)

        coEvery { remoteProjectDataSource.getProjects() } returns remoteData.right()
        coEvery { localProjectDataSource.getProjects() } returns localData
        coEvery { localProjectDataSource.insertProjects(any()) } answers {
            coEvery { localProjectDataSource.getProjects() } returns firstArg()
        }

        runTest(dispatcher) {
            val result = repository.getProjects(true).orNull()

            result.shouldNotBeNull()
            result shouldContainSame remoteData
        }
    }

    @Test
    fun `GIVEN remote data older than local data WHEN getProjects THEN return remote data with changes`() {
        val result = buildSuccessfulResult()

        val remote1 = buildProject(id = 1, updatedAt = "2021-05-01T00:00:00Z")
        val remote2 = buildProject(id = 2, updatedAt = "2021-05-01T00:00:00Z")
        val remoteData = listOf(remote1, remote2)

        val local1 = buildProject(id = 1, updatedAt = "2021-04-29T00:00:00Z")
        val local2 = buildProject(id = 2, updatedAt = "2021-05-15T00:00:00Z")
        val localData = listOf(local1, local2)

        coEvery { remoteProjectDataSource.getProjects() } returns remoteData.right()
        coEvery { localProjectDataSource.getProjects() } returns localData
        coEvery { remoteProjectDataSource.updateProject(any()) } returns result.right()
        coEvery { localProjectDataSource.insertProjects(any()) } answers {
            coEvery { localProjectDataSource.getProjects() } returns firstArg()
        }

        runTest(dispatcher) {
            val repositoryResult = repository.getProjects(true).orNull()
            val merged1 = repositoryResult?.find { it.id == 1 }
            val merged2 = repositoryResult?.find { it.id == 2 }

            merged1.shouldNotBeNull()
            merged1 shouldBeEqualTo remote1

            merged2.shouldNotBeNull()
            merged2 shouldBeEqualTo local2
        }
    }

    @Test
    fun `GIVEN no local data WHEN getProjects THEN return remote data`() {
        val result = buildSuccessfulResult()

        val remote1 = buildProject(id = 1)
        val remote2 = buildProject(id = 2)
        val remoteData = listOf(remote1, remote2)

        coEvery { remoteProjectDataSource.getProjects() } returns remoteData.right()
        coEvery { localProjectDataSource.getProjects() } returns emptyList()
        coEvery { remoteProjectDataSource.updateProject(any()) } returns result.right()
        coEvery { localProjectDataSource.insertProjects(any()) } answers {
            coEvery { localProjectDataSource.getProjects() } returns firstArg()
        }

        runTest(dispatcher) {
            val repositoryResult = repository.getProjects(true).orNull()

            repositoryResult.shouldNotBeNull()
            repositoryResult shouldContainSame remoteData
        }
    }

    @Test
    fun `GIVEN deletedAt greater than DEFAULT_DELETED_AT WHEN getProjects THEN delete expected projects`() {
        val result = buildSuccessfulResult()

        val remote1 = buildProject(id = 1, updatedAt = "2021-05-01T00:00:00Z")
        val remote2 = buildProject(id = 2, updatedAt = "2021-05-01T00:00:00Z")
        val remoteData = listOf(remote1, remote2)

        val local1 = buildProject(id = 1, updatedAt = "2021-04-01T00:00:00Z", deletedAt = 7889)
        val local2 = buildProject(id = 2, updatedAt = "2021-04-01T00:00:00Z")
        val localData = listOf(local1, local2)

        coEvery { remoteProjectDataSource.getProjects() } returns remoteData.right()
        coEvery { localProjectDataSource.getProjects() } returns localData
        coEvery { localProjectDataSource.insertProjects(any()) } answers {
            coEvery { localProjectDataSource.getProjects() } returns firstArg()
        }
        coEvery { remoteProjectDataSource.deleteProject(any()) } returns result.right()

        runTest(dispatcher) {
            val repositoryResult = repository.getProjects(true).orNull()

            repositoryResult.shouldNotBeNull()
            repositoryResult shouldContain remote2
            repositoryResult shouldNotContain remote1
        }
    }

    @Test
    fun `GIVEN a unsuccessful result (delete) WHEN getProjects THEN return the expected DeleteProjectException`() {
        val result = buildUnSuccessfulResult()

        val remote1 = buildProject(id = 1, updatedAt = "2021-05-01T00:00:00Z")
        val remote2 = buildProject(id = 2, updatedAt = "2021-05-01T00:00:00Z")
        val remoteData = listOf(remote1, remote2)

        val local1 = buildProject(id = 1, updatedAt = "2021-04-01T00:00:00Z", deletedAt = 7889)
        val local2 = buildProject(id = 2, updatedAt = "2021-04-01T00:00:00Z")
        val localData = listOf(local1, local2)

        coEvery { remoteProjectDataSource.getProjects() } returns remoteData.right()
        coEvery { localProjectDataSource.getProjects() } returns localData
        coEvery { remoteProjectDataSource.deleteProject(any()) } returns result.right()

        runTest(dispatcher) {
            val repositoryResult = repository.getProjects(true).swap().orNull()

            repositoryResult.shouldNotBeNull()
            repositoryResult shouldBeInstanceOf DeleteProjectException::class
            repositoryResult.message shouldBeEqualTo result.message
        }
    }

    @Test
    fun `GIVEN a unsuccessful result (update) WHEN getProjects THEN return the expected UpdateProjectException`() {
        val result = buildUnSuccessfulResult()

        val remote1 = buildProject(id = 1, updatedAt = "2021-05-01T00:00:00Z")
        val remote2 = buildProject(id = 2, updatedAt = "2021-05-01T00:00:00Z")
        val remoteData = listOf(remote1, remote2)

        val local1 = buildProject(id = 1, updatedAt = "2021-04-29T00:00:00Z")
        val local2 = buildProject(id = 2, updatedAt = "2021-05-15T00:00:00Z")
        val localData = listOf(local1, local2)

        coEvery { remoteProjectDataSource.getProjects() } returns remoteData.right()
        coEvery { localProjectDataSource.getProjects() } returns localData
        coEvery { remoteProjectDataSource.updateProject(any()) } returns result.right()

        runTest(dispatcher) {
            val repositoryResult = repository.getProjects(true).swap().orNull()

            repositoryResult.shouldNotBeNull()
            repositoryResult shouldBeInstanceOf UpdateProjectException::class
            repositoryResult.message shouldBeEqualTo result.message
        }
    }

    @Test
    fun `GIVEN a non null project WHEN getProjectById THEN return the expected Project`() {
        val project = buildProject()

        coEvery { localProjectDataSource.getProjectById(any()) } returns project

        runTest(dispatcher) {
            val result = repository.getProjectById(project.id).orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo project
        }
    }

    @Test
    fun `GIVEN updateInRemote equal to false WHEN updateProject THEN updateProject (remote) should never be called and insertProjects should be called once`() {
        val project = buildProject()

        runTest(dispatcher) {
            repository.updateProject(project, false)

            coVerifyNever {
                remoteProjectDataSource.updateProject(any())
            }

            coVerifyOnce {
                localProjectDataSource.insertProjects(any())
            }
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN updateProject THEN return the expected Exception`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)
        val project = buildProject()

        coEvery { remoteProjectDataSource.updateProject(any()) } returns exception.left()

        runTest(dispatcher) {
            val result = repository.updateProject(project, true).swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo exception
        }
    }

    @Test
    fun `GIVEN a successful result WHEN updateProject THEN insertProjects and getProjectById should be called once`() {
        val result = buildSuccessfulResult()
        val project = buildProject()
        val projects = listOf(project)

        coEvery { remoteProjectDataSource.updateProject(any()) } returns result.right()

        runTest(dispatcher) {
            repository.updateProject(project, true)

            coVerifyOnce {
                localProjectDataSource.insertProjects(projects)
                localProjectDataSource.getProjectById(project.id)
            }
        }
    }

    @Test
    fun `GIVEN a unsuccessful result WHEN updateProject THEN return the expected UpdateProjectException`() {
        val result = buildUnSuccessfulResult()
        val project = buildProject()

        coEvery { remoteProjectDataSource.updateProject(any()) } returns result.right()

        runTest(dispatcher) {
            val repositoryResult = repository.updateProject(project, true).swap().orNull()

            repositoryResult.shouldNotBeNull()
            repositoryResult shouldBeInstanceOf UpdateProjectException::class
            repositoryResult.message shouldBeEqualTo result.message
        }
    }

    @Test
    fun `GIVEN deleteInRemote = true WHEN deleteProject THEN call the expected methods`() {
        val result = buildSuccessfulResult()
        val project = buildProject()

        coEvery { remoteProjectDataSource.deleteProject(any()) } returns result.right()

        runTest(dispatcher) {
            repository.deleteProject(project, true)

            coVerifyOnce {
                remoteProjectDataSource.deleteProject(project)
                localProjectDataSource.deleteProject(project)
            }

            coVerifyNever {
                localProjectDataSource.markAsDeleted(any(), any())
            }
        }
    }

    @Test
    fun `GIVEN deleteInRemote = false WHEN deleteProject THEN call the expected methods`() {
        val result = buildSuccessfulResult()
        val project = buildProject()

        coEvery { remoteProjectDataSource.deleteProject(any()) } returns result.right()

        runTest(dispatcher) {
            repository.deleteProject(project, false)

            coVerifyOnce {
                localProjectDataSource.markAsDeleted(project.id, any())
            }

            coVerifyNever {
                remoteProjectDataSource.deleteProject(project)
                localProjectDataSource.deleteProject(project)
            }
        }
    }

    @Test
    fun `GIVEN a successful result WHEN deleteProject THEN return Unit`() {
        val result = buildSuccessfulResult()
        val project = buildProject()

        coEvery { remoteProjectDataSource.deleteProject(any()) } returns result.right()

        runTest(dispatcher) {
            val repositoryResult = repository.deleteProject(project, true).orNull()

            repositoryResult.shouldNotBeNull()
            repositoryResult shouldBeInstanceOf Unit::class
        }
    }

    @Test
    fun `GIVEN a unsuccessful result WHEN deleteProject THEN return the expected DeleteProjectException`() {
        val result = buildUnSuccessfulResult()
        val project = buildProject()

        coEvery { remoteProjectDataSource.deleteProject(any()) } returns result.right()

        runTest(dispatcher) {
            val repositoryResult = repository.deleteProject(project, true).swap().orNull()

            repositoryResult.shouldNotBeNull()
            repositoryResult shouldBeInstanceOf DeleteProjectException::class
            repositoryResult.message shouldBeEqualTo result.message
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN deleteProject THEN return the expected Exception`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)
        val project = buildProject()

        coEvery { remoteProjectDataSource.deleteProject(any()) } returns exception.left()

        runTest(dispatcher) {
            val result = repository.deleteProject(project, true).swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo exception
        }
    }
}

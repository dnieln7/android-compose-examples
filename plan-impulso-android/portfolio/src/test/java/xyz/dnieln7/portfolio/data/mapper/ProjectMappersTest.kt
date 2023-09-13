package xyz.dnieln7.portfolio.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import xyz.dnieln7.portfolio.fake.buildProject
import xyz.dnieln7.portfolio.fake.buildProjectDTO
import xyz.dnieln7.portfolio.fake.buildProjectDbModel

class ProjectMappersTest {

    @Test
    fun `GIVEN a Project WHEN toUpdateProjectRequest THEN get the expected UpdateProjectRequest`() {
        val project = buildProject()
        val result = project.toUpdateProjectRequest()

        with(result) {
            id shouldBeEqualTo project.id
            name shouldBeEqualTo project.name
            ownership shouldBeEqualTo project.ownership
            summary shouldBeEqualTo project.summary
            year shouldBeEqualTo project.year
            importance shouldBeEqualTo project.importance
            thumbnail shouldBeEqualTo project.thumbnail
            images shouldBeEqualTo project.images
            tags shouldBeEqualTo project.tags
            duration shouldBeEqualTo project.duration
            description shouldBeEqualTo project.description
            features shouldBeEqualTo project.features
            technologies shouldBeEqualTo project.technologies
            androidGit shouldBeEqualTo project.androidGit
            androidUrl shouldBeEqualTo project.androidUrl
            webUrl shouldBeEqualTo project.webUrl
            webGit shouldBeEqualTo project.webGit
            programUrl shouldBeEqualTo project.programUrl
            programGit shouldBeEqualTo project.programGit
        }
    }

    @Test
    fun `GIVEN a ProjectDTO WHEN toDomain THEN get the expected Project`() {
        val projectDTO = buildProjectDTO()
        val result = projectDTO.toDomain()

        with(result) {
            id shouldBeEqualTo projectDTO.id
            name shouldBeEqualTo projectDTO.name
            ownership shouldBeEqualTo projectDTO.ownership
            summary shouldBeEqualTo projectDTO.summary
            year shouldBeEqualTo projectDTO.year
            importance shouldBeEqualTo projectDTO.importance
            thumbnail shouldBeEqualTo projectDTO.thumbnail
            images shouldBeEqualTo projectDTO.images
            tags shouldBeEqualTo projectDTO.tags
            duration shouldBeEqualTo projectDTO.duration
            description shouldBeEqualTo projectDTO.description
            features shouldBeEqualTo projectDTO.features
            technologies shouldBeEqualTo projectDTO.technologies
            androidGit shouldBeEqualTo projectDTO.androidGit
            androidUrl shouldBeEqualTo projectDTO.androidUrl
            webUrl shouldBeEqualTo projectDTO.webUrl
            webGit shouldBeEqualTo projectDTO.webGit
            programUrl shouldBeEqualTo projectDTO.programUrl
            programGit shouldBeEqualTo projectDTO.programGit
            createdAt shouldBeEqualTo projectDTO.createdAt
            updatedAt shouldBeEqualTo projectDTO.updatedAt
            deletedAt shouldBeEqualTo DEFAULT_DELETED_AT
        }
    }

    @Test
    fun `GIVEN a ProjectDbModel WHEN toDomain THEN get the expected Project`() {
        val projectDbModel = buildProjectDbModel()
        val result = projectDbModel.toDomain()

        with(result) {
            id shouldBeEqualTo projectDbModel.id
            name shouldBeEqualTo projectDbModel.name
            ownership shouldBeEqualTo projectDbModel.ownership
            summary shouldBeEqualTo projectDbModel.summary
            year shouldBeEqualTo projectDbModel.year
            importance shouldBeEqualTo projectDbModel.importance
            thumbnail shouldBeEqualTo projectDbModel.thumbnail
            images shouldBeEqualTo projectDbModel.images
            tags shouldBeEqualTo projectDbModel.tags
            duration shouldBeEqualTo projectDbModel.duration
            description shouldBeEqualTo projectDbModel.description
            features shouldBeEqualTo projectDbModel.features
            technologies shouldBeEqualTo projectDbModel.technologies
            androidGit shouldBeEqualTo projectDbModel.androidGit
            androidUrl shouldBeEqualTo projectDbModel.androidUrl
            webUrl shouldBeEqualTo projectDbModel.webUrl
            webGit shouldBeEqualTo projectDbModel.webGit
            programUrl shouldBeEqualTo projectDbModel.programUrl
            programGit shouldBeEqualTo projectDbModel.programGit
            createdAt shouldBeEqualTo projectDbModel.createdAt
            updatedAt shouldBeEqualTo projectDbModel.updatedAt
            deletedAt shouldBeEqualTo deletedAt
        }
    }

    @Test
    fun `GIVEN a Project WHEN toDbModel THEN get the expected ProjectDbModel`() {
        val project = buildProject()
        val result = project.toDbModel()

        with(result) {
            id shouldBeEqualTo project.id
            name shouldBeEqualTo project.name
            ownership shouldBeEqualTo project.ownership
            summary shouldBeEqualTo project.summary
            year shouldBeEqualTo project.year
            importance shouldBeEqualTo project.importance
            thumbnail shouldBeEqualTo project.thumbnail
            images shouldBeEqualTo project.images
            tags shouldBeEqualTo project.tags
            duration shouldBeEqualTo project.duration
            description shouldBeEqualTo project.description
            features shouldBeEqualTo project.features
            technologies shouldBeEqualTo project.technologies
            androidGit shouldBeEqualTo project.androidGit
            androidUrl shouldBeEqualTo project.androidUrl
            webUrl shouldBeEqualTo project.webUrl
            webGit shouldBeEqualTo project.webGit
            programUrl shouldBeEqualTo project.programUrl
            programGit shouldBeEqualTo project.programGit
            createdAt shouldBeEqualTo project.createdAt
            updatedAt shouldBeEqualTo project.updatedAt
            deletedAt shouldBeEqualTo project.deletedAt
        }
    }
}

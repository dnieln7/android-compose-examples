package xyz.dnieln7.portfolio.data.datasource

import xyz.dnieln7.portfolio.data.local.dao.ProjectDbModelDao
import xyz.dnieln7.portfolio.data.mapper.toDbModel
import xyz.dnieln7.portfolio.data.mapper.toDomain
import xyz.dnieln7.portfolio.domain.model.Project

class LocalProjectDataSource(private val projectDbModelDao: ProjectDbModelDao) {

    suspend fun insertProjects(projects: List<Project>) {
        return projectDbModelDao.insertProjects(projects.map { it.toDbModel() })
    }

    suspend fun getProjects(): List<Project> {
        return projectDbModelDao.getProjects().map { it.toDomain() }
    }

    suspend fun getProjectById(id: Int): Project? {
        return projectDbModelDao.getProjectById(id)?.toDomain()
    }

    suspend fun markAsDeleted(id: Int, deletedAt: Long) {
        return projectDbModelDao.markAsDeleted(id, deletedAt)
    }

    suspend fun deleteProjects() {
        projectDbModelDao.deleteProjects()
    }

    suspend fun deleteProject(project: Project) {
        projectDbModelDao.deleteProjectById(project.id)
    }
}

package xyz.dnieln7.portfolio.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import xyz.dnieln7.portfolio.data.local.model.ProjectDbModel

@Dao
interface ProjectDbModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectDbModel>)

    @Query("SELECT * FROM tb_projects")
    suspend fun getProjects(): List<ProjectDbModel>

    @Query("SELECT * FROM tb_projects WHERE id = :id")
    suspend fun getProjectById(id: Int): ProjectDbModel?

    @Query("UPDATE tb_projects SET deletedAt = :deletedAt WHERE id = :id")
    suspend fun markAsDeleted(id: Int, deletedAt: Long)

    @Query("DELETE FROM tb_projects")
    suspend fun deleteProjects()

    @Query("DELETE FROM tb_projects WHERE id = :id")
    suspend fun deleteProjectById(id: Int)
}

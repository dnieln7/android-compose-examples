package xyz.dnieln7.portfolio.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_projects")
data class ProjectDbModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "ownership")
    val ownership: String,
    @ColumnInfo(name = "summary")
    val summary: String,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "importance")
    val importance: Double,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String,
    @ColumnInfo(name = "images")
    val images: List<String>,
    @ColumnInfo(name = "tags")
    val tags: List<String>,
    @ColumnInfo(name = "duration")
    val duration: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "features")
    val features: List<String>,
    @ColumnInfo(name = "technologies")
    val technologies: List<String>,
    @ColumnInfo(name = "androidGit")
    val androidGit: String,
    @ColumnInfo(name = "androidUrl")
    val androidUrl: String,
    @ColumnInfo(name = "webUrl")
    val webUrl: String,
    @ColumnInfo(name = "webGit")
    val webGit: String,
    @ColumnInfo(name = "programUrl")
    val programUrl: String,
    @ColumnInfo(name = "programGit")
    val programGit: String,
    @ColumnInfo(name = "createdAt")
    val createdAt: String,
    @ColumnInfo(name = "updatedAt")
    val updatedAt: String,
    @ColumnInfo(name = "deletedAt", defaultValue = "0")
    val deletedAt: Long,
)

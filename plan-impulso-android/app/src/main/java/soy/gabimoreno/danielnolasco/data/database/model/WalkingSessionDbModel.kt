package soy.gabimoreno.danielnolasco.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_walking_sessions")
data class WalkingSessionDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "startTime")
    val startTime: Long,
    @ColumnInfo(name = "endTime")
    val endTime: Long?,
    @ColumnInfo(name = "duration")
    val duration: Long
)

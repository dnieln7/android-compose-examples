package soy.gabimoreno.danielnolasco.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import soy.gabimoreno.danielnolasco.data.database.dao.LocationEventDbModelDao
import soy.gabimoreno.danielnolasco.data.database.dao.WalkingSessionDbModelDao
import soy.gabimoreno.danielnolasco.data.database.model.LocationEventDbModel
import soy.gabimoreno.danielnolasco.data.database.model.WalkingSessionDbModel

@Database(
    entities = [
        WalkingSessionDbModel::class,
        LocationEventDbModel::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class WalkingDatabase : RoomDatabase() {
    abstract fun walkingSessionDbModelDao(): WalkingSessionDbModelDao
    abstract fun locationEventDbModelDao(): LocationEventDbModelDao

    companion object {
        fun build(context: Context): WalkingDatabase {
            return Room.databaseBuilder(
                context,
                WalkingDatabase::class.java,
                "walking_database"
            ).build()
        }
    }
}

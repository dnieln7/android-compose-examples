package soy.gabimoreno.danielnolasco.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import soy.gabimoreno.danielnolasco.data.database.dao.DogDbModelDao
import soy.gabimoreno.danielnolasco.data.database.model.DogDbModel

@Database(
    entities = [DogDbModel::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class PetDatabase : RoomDatabase() {
    abstract fun dogDbModelDao(): DogDbModelDao

    companion object {
        fun build(context: Context): PetDatabase {
            return Room.databaseBuilder(
                context,
                PetDatabase::class.java,
                "pet_database"
            ).build()
        }
    }
}

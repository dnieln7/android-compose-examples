package xyz.dnieln7.portfolio.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.dnieln7.portfolio.data.local.converter.StringListConverters
import xyz.dnieln7.portfolio.data.local.dao.ProjectDbModelDao
import xyz.dnieln7.portfolio.data.local.model.ProjectDbModel

@Database(
    entities = [
        ProjectDbModel::class,
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
)
@TypeConverters(
    value = [
        StringListConverters::class,
    ]
)
abstract class PortfolioDatabase : RoomDatabase() {

    abstract fun projectDbModelDao(): ProjectDbModelDao

    companion object {
        fun build(context: Context): PortfolioDatabase {
            return Room.databaseBuilder(
                context,
                PortfolioDatabase::class.java,
                "portfolio_database"
            ).build()
        }
    }
}

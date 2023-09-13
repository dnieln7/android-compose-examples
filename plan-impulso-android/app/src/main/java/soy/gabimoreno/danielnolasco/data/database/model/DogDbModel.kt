package soy.gabimoreno.danielnolasco.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_dogs")
data class DogDbModel(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "barking")
    val barking: Int,
    @ColumnInfo(name = "coatLength")
    val coatLength: Int,
    @ColumnInfo(name = "drooling")
    val drooling: Int,
    @ColumnInfo(name = "energy")
    val energy: Int,
    @ColumnInfo(name = "goodWithChildren")
    val goodWithChildren: Int,
    @ColumnInfo(name = "goodWithOtherDogs")
    val goodWithOtherDogs: Int,
    @ColumnInfo(name = "goodWithStrangers")
    val goodWithStrangers: Int,
    @ColumnInfo(name = "grooming")
    val grooming: Int,
    @ColumnInfo(name = "imageLink")
    val imageLink: String,
    @ColumnInfo(name = "maxHeightFemale")
    val maxHeightFemale: Double,
    @ColumnInfo(name = "maxHeightMale")
    val maxHeightMale: Double,
    @ColumnInfo(name = "maxLifeExpectancy")
    val maxLifeExpectancy: Double,
    @ColumnInfo(name = "maxWeightFemale")
    val maxWeightFemale: Double,
    @ColumnInfo(name = "maxWeightMale")
    val maxWeightMale: Double,
    @ColumnInfo(name = "minHeightFemale")
    val minHeightFemale: Double,
    @ColumnInfo(name = "minHeightMale")
    val minHeightMale: Double,
    @ColumnInfo(name = "minLifeExpectancy")
    val minLifeExpectancy: Double,
    @ColumnInfo(name = "minWeightFemale")
    val minWeightFemale: Double,
    @ColumnInfo(name = "minWeightMale")
    val minWeightMale: Double,
    @ColumnInfo(name = "playfulness")
    val playfulness: Int,
    @ColumnInfo(name = "protectiveness")
    val protectiveness: Int,
    @ColumnInfo(name = "shedding")
    val shedding: Int,
    @ColumnInfo(name = "trainability")
    val trainability: Int
)

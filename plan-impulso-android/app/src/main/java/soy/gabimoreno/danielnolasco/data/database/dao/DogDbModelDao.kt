package soy.gabimoreno.danielnolasco.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import soy.gabimoreno.danielnolasco.data.database.model.DogDbModel

@Dao
interface DogDbModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogDbModels(entities: List<DogDbModel>)

    @Query("SELECT * FROM tb_dogs")
    suspend fun getDogDbModels(): List<DogDbModel>

    @Query("SELECT * FROM tb_dogs")
    fun observeDogDbModels(): PagingSource<Int, DogDbModel>

    @Query("SELECT * FROM tb_dogs WHERE name = :name")
    suspend fun getDogDbModelsByName(name: String): DogDbModel?

    @Query("DELETE FROM tb_dogs")
    suspend fun deleteDogDbModels()

    @Query("SELECT COUNT(name) FROM tb_dogs")
    suspend fun count(): Int
}

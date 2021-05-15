package com.udacity.asteroidradar.databasa

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.model.entity.Asteroid
import java.time.LocalDate

@Dao
interface AsteroidDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsteroid(vararg asteroid: Asteroid)

    @Query("SELECT * FROM asteroid_table")
    suspend fun getAllAsteroids(): List<Asteroid>


    @Query("SELECT * FROM asteroid_table ORDER BY  closeApproachDate ASC")
    suspend fun getAllAsteroidsByDay(): List<Asteroid>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate =:date LIMIT 7")
    suspend fun getLimitedAsteroidsByDay(date: String): List<Asteroid>

    @Query("DELETE  FROM asteroid_table")
    suspend fun deleteAsteroid()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(vararg pictureOfDay: PictureOfDay)

    @Query("SELECT * FROM picture_of_day")
    suspend fun getPictureOfDay(): PictureOfDay

    @Query("DELETE  FROM picture_of_day")
    suspend fun deletePicture()
}
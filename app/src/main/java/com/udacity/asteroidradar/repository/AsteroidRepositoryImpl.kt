package com.udacity.asteroidradar.repository

import android.util.Log
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.databasa.AsteroidDatabase
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.model.entity.Asteroid
import org.json.JSONObject

class AsteroidRepositoryImpl(private val database: AsteroidDatabase) : AsteroidRepository {
    private val service = RetrofitClient


    override suspend fun fetchAllAsteroidApi(
        startDate: String
    ): NetworkResult<String> {
        return try {
            val response = service.asteroidService.getAsteroidData(startDate)
            if (response.isSuccessful) {
                val data = response.body()

                data?.let { asteroids ->
                    Log.d("fetchAsteroid", "data: asteroids $asteroids")
                    fetchAsteroid(asteroids)
                    return@let NetworkResult.success(asteroids)
                } ?: NetworkResult.error("no  data", null)

            } else {
                Log.d("fetchAsteroid", "response:  ${response.errorBody()}")
                NetworkResult.error("${response.message()}: no data response", null)
            }
        } catch (ex: Exception) {
            Log.d("fetchAsteroid", "Exception: ex ${ex.message}")
            NetworkResult.error("${ex.localizedMessage}  no data catch ex", null)
        }
    }

    override suspend fun fetchAllAsteroidRoom(onComplete: (Status, List<Asteroid>) -> Unit) {
        onComplete(Status.SUCCESS, database.asteroidDao().getAllAsteroids())
    }

    override suspend fun fetchAllAsteroidRoomOrderByDate(onComplete: (Status, List<Asteroid>) -> Unit) {
        onComplete(Status.SUCCESS, database.asteroidDao().getAllAsteroidsByDay())
    }

    private suspend fun fetchAsteroid(asteroids: String) {
        val resAsteroid = JSONObject(asteroids)
        val data = parseAsteroidsJsonResult(resAsteroid)
        database.asteroidDao().deleteAsteroid()
        database.asteroidDao().insertAsteroid(*data.toTypedArray())
        Log.d("fetchJSONObject", "insert: asteroids $data")
    }

    override suspend fun getAsteroidImage(): NetworkResult<PictureOfDay> {
        return try {
            val response = service.asteroidImageService.getImageFromApi()
            if (response.isSuccessful && response.code() == 200) {
                val data = response.body()
                data?.let {
                    val imageObject = JSONObject(it)
                   val imageData = parseJsonResultToImageClass(imageObject)
                    Log.d("messageAsteroid", "fetchAllAsteroid: isSuccessful $imageData :yaradildi")
                    return@let NetworkResult.success(imageData)
                } ?: NetworkResult.error(response.message() ?: "no data catch ex", null)
            } else {

                NetworkResult.error(response.message() ?: "no data catch ex", null)
            }
        } catch (e: java.lang.Exception) {
            NetworkResult.error(e.localizedMessage ?: "no data catch ex", null)
        }
    }

    override suspend fun fetchLimitedAsteroidRoom(date:String,onComplete: (Status, List<Asteroid>) -> Unit) {
        onComplete(Status.SUCCESS,database.asteroidDao().getLimitedAsteroidsByDay(date))
    }

    suspend fun delete() {
        database.asteroidDao().deleteAsteroid()
    }
}
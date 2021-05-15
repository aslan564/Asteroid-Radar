package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.api.NetworkResult
import com.udacity.asteroidradar.api.Status
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.model.entity.Asteroid
import com.udacity.asteroidradar.model.pojo.AsteroidResponse
import org.json.JSONObject

interface AsteroidRepository {
    suspend fun fetchAllAsteroidApi(startDate: String):NetworkResult<String>
    suspend fun fetchAllAsteroidRoom(onComplete:(Status,List<Asteroid>)->Unit)
    suspend fun fetchAllAsteroidRoomOrderByDate(onComplete:(Status,List<Asteroid>)->Unit)
    suspend fun fetchLimitedAsteroidRoom(date:String,onComplete:(Status,List<Asteroid>)->Unit)
    suspend fun getAsteroidImage():NetworkResult<PictureOfDay>
}
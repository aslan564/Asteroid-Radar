package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.api.NetworkResult
import com.udacity.asteroidradar.api.Status
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.model.entity.Asteroid
import com.udacity.asteroidradar.model.pojo.AsteroidResponse
import org.json.JSONObject

interface AsteroidRepository {
    suspend fun fetchAllAsteroidApi(startDate: String): NetworkResult<String>
    fun getAllAsteroidRoom(): LiveData<List<Asteroid>>
    fun getAllAsteroidRoomOrderByDate(): LiveData<List<Asteroid>>
    fun getLimitedAsteroidRoom(date: String): LiveData<List<Asteroid>>
    suspend fun fetchAsteroidImageApi(): NetworkResult<PictureOfDay>
    fun getDayImage(): LiveData<PictureOfDay>
}
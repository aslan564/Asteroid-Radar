package com.udacity.asteroidradar.api.service

import com.udacity.asteroidradar.model.PictureOfDay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidPictureService {

    @GET("planetary/apod")
    suspend fun getImageFromApi(): Response<String>
}
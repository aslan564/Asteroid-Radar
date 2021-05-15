package com.udacity.asteroidradar.api.service

import com.udacity.asteroidradar.model.pojo.AsteroidResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidService {

//    https://api.nasa.gov/neo/rest/v1/feed?start_date=2021-05-15&end_date=2021-05-20&api_key=EGSzPhhpC5iFb0mqVp5H9GpDA1r5SX79OoJeIr9J

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidData(
        @Query("start_date") startDate: String

    ):Response<String>

    /*  @Query("end_date") endDate: String*/

    @GET("neo/rest/v1/feed")
    fun getFutureWeather(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") languageCode: String = "en"
    )//: Deferred<FeatureWeatherResponse>
}
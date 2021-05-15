package com.udacity.asteroidradar.model.pojo


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AsteroidResponse(
    @Json(name = "element_count")
    val elementCount: Int,
    @Json(name = "near_earth_objects")
    val nearEarthObjects: NearEarthObjects
)
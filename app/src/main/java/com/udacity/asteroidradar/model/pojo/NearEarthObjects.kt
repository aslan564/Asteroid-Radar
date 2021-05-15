package com.udacity.asteroidradar.model.pojo


import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class NearEarthObjects  constructor(
    val x20200907: List<DayPOJO>,
)
@RequiresApi(Build.VERSION_CODES.O)
enum class Week(val start_date: String, val end_date:String ) {
    TODAY( LocalDate.now().toString(),LocalDate.now().plusDays(5).toString()),
}
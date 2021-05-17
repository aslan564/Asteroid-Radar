package com.udacity.asteroidradar.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

object Constants {
    const val WORK_NAME = "RefreshDataWorker"
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val JSON_EARTH_OBJECTS = "nearEarthObjects"

}

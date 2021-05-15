package com.udacity.asteroidradar.util

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.api.Status
import com.udacity.asteroidradar.model.entity.Asteroid
import com.udacity.asteroidradar.model.pojo.AsteroidResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun statusVisibility(status: Status): Boolean {
    return when (status) {
        Status.ERROR -> {
            true
        }
        Status.SUCCESS -> {
            true
        }
        Status.LOADING -> {
            false
        }
    }
}


fun AsteroidResponse.parseAsteroidsAsteroidResponse(): ArrayList<Asteroid> {
    val asteroidList = ArrayList<Asteroid>()
    val asteroidResponseList = this.nearEarthObjects.x20200907

    for (item in asteroidResponseList) {
        val id = item.id.toLong()
        val codename = item.name
        val absoluteMagnitude = item.absoluteMagnitudeH.toString().toDouble()
        val estimatedDiameter =
            item.estimatedDiameter.kilometers.estimatedDiameterMax.toString().toDouble()
        val isPotentiallyHazardous = item.isPotentiallyHazardousAsteroid
        for (velCity in item.closeApproachData) {
            val relativeVelocity = velCity.relativeVelocity.kilometersPerSecond.toDouble()
            val distanceFromEarth = velCity.missDistance.kilometers.toDouble()
            val asteroid = Asteroid(
                id, codename, velCity.closeApproachDate, absoluteMagnitude,
                estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous
            )
            asteroidList.add(asteroid)
        }


    }


    return asteroidList
}

@SuppressLint("WeekBasedYear")
private fun getNextSevenDaysFormatted(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}
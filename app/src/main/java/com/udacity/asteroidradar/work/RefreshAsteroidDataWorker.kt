package com.udacity.asteroidradar.work

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.databasa.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepositoryImpl
import com.udacity.asteroidradar.work.DateLocal.START_DATE
import retrofit2.HttpException
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
object DateLocal {
    val START_DATE = LocalDate.now().toString()
    val END_DATE = LocalDate.now().plusDays(6).toString()
}

class RefreshAsteroidDataWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val database = AsteroidDatabase(applicationContext)
        val repository = AsteroidRepositoryImpl(database)
        return try {
            repository.fetchAllAsteroidApi(START_DATE)
            Result.success()
        } catch (ex: HttpException) {
            Result.retry()
        }
    }
}


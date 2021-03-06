package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.util.Constants.WORK_NAME
import com.udacity.asteroidradar.util.SharedPreferenceManager
import com.udacity.asteroidradar.work.RefreshAsteroidDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication: Application() {

    private val applicationScope= CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        SharedPreferenceManager.instance(this)
        delayInit()
    }

    private fun delayInit()=applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork(){

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.METERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        val repeatingRequest= PeriodicWorkRequestBuilder<RefreshAsteroidDataWorker>(1,TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

}
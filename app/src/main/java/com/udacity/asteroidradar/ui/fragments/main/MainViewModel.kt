package com.udacity.asteroidradar.ui.fragments.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.databasa.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepositoryImpl
import com.udacity.asteroidradar.util.SharedPreferenceManager
import com.udacity.asteroidradar.work.DateLocal.START_DATE
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase(application)
    private val repositoryImpl = AsteroidRepositoryImpl(database)

    fun refreshFirstData(login: Boolean) {
        if (!login) {
            SharedPreferenceManager.isLogin=true
            Log.d("MainFragments", "refreshFirstData : $login ")
            viewModelScope.launch {
                repositoryImpl.fetchAllAsteroidApi(START_DATE)
                repositoryImpl.fetchAsteroidImageApi()
            }
        }
    }

    val asteroidLIst = repositoryImpl.getAllAsteroidRoom()
    val asteroidOrderByDayLIst = repositoryImpl.getAllAsteroidRoomOrderByDate()
    val asteroidLimitedLIst = repositoryImpl.getLimitedAsteroidRoom(START_DATE)
    val pictureOfDay = repositoryImpl.getDayImage()


    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
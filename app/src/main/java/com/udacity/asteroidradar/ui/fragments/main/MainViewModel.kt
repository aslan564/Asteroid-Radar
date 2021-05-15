package com.udacity.asteroidradar.ui.fragments.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.Status
import com.udacity.asteroidradar.databasa.AsteroidDatabase
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.model.entity.Asteroid
import com.udacity.asteroidradar.repository.AsteroidRepositoryImpl
import com.udacity.asteroidradar.util.Sorted
import com.udacity.asteroidradar.work.DateLocal
import com.udacity.asteroidradar.work.DateLocal.START_DATE
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase(application)
    private val repositoryImpl = AsteroidRepositoryImpl(database)

    private var _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidLIst: LiveData<List<Asteroid>> = _asteroidList


    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay> = _pictureOfDay

    private var _uiState = MutableLiveData<Status>()
    val uiState: LiveData<Status>
        get() = _uiState

    init {
        fetchAsteroid()
    }


    fun sortedAsteroidList(sorted: Sorted) = viewModelScope.launch {
        _uiState.postValue(Status.LOADING)
        when (sorted) {
            Sorted.ALL -> {
                repositoryImpl.fetchAllAsteroidRoom { status, list ->
                    _asteroidList.postValue(list)
                    _uiState.postValue(status)
                }
            }
            Sorted.DATE -> {
                repositoryImpl.fetchAllAsteroidRoomOrderByDate { status, list ->
                    _asteroidList.postValue(list)
                    _uiState.postValue(status)
                }
            }
            Sorted.TODAY -> {
                repositoryImpl.fetchLimitedAsteroidRoom(START_DATE) { status, list ->
                    _asteroidList.postValue(list)
                    _uiState.postValue(status)
                }
            }
        }
    }


    private fun fetchAsteroid() {
        viewModelScope.launch {
            repositoryImpl.fetchAllAsteroidApi(START_DATE)

            val response = repositoryImpl.getAsteroidImage()
            when (response.status) {
                Status.SUCCESS -> {
                    _pictureOfDay.postValue(response.data)
                    Log.d("messageAsteroid", "fetchAsteroid: ${response.data} :yaradildi")
                }

                else -> {
                    Log.d("messageAsteroid", "fetchAsteroid: ViewModel  else")
                }

            }
        }
    }


}
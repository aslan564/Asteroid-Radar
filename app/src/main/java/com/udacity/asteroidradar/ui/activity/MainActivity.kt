package com.udacity.asteroidradar.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.util.SharedPreferenceManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

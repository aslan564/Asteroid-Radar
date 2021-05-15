package com.udacity.asteroidradar.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPreferenceManager {

    private const val SHARED_PREFERENCES_FILE_KEY = "com.udacity.asteroidradar.util"
    private lateinit var instance: SharedPreferenceManager

    private lateinit var sharedPreference: SharedPreferences

    private val IS_LOGIN = Pair("is_login", false)

    fun instance(context: Context) {
        sharedPreference= context.getSharedPreferences(SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE)
    }

    //an inline function to put variable and save it
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    //SharedPreferences variables getters/setters
    var isLogin: Boolean
        get() = sharedPreference.getBoolean(IS_LOGIN.first, IS_LOGIN.second)
        set(value) = sharedPreference.edit {
            it.putBoolean(IS_LOGIN.first, value)
        }


}
package com.example.simple_game.util

import android.content.Context
import android.content.SharedPreferences

object AppPreference {

    private const val NAME_PREF = "preference"
    private const val BEST_1 = "best1"
    private const val BEST_2 = "best2"
    private const val BEST_3 = "best3"
    private const val BEST_4 = "best4"
    private const val BEST_5 = "best5"

    private lateinit var preferences: SharedPreferences

    fun getPreference(context: Context): SharedPreferences {
        preferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return preferences
    }

    fun getPreferencesScore(): List<Int> {
        return listOf(
            preferences.getInt(BEST_1, -1),
            preferences.getInt(BEST_2, -1),
            preferences.getInt(BEST_3, -1),
            preferences.getInt(BEST_4, -1),
            preferences.getInt(BEST_5, -1),
            -1
        ).sorted()
    }

    fun setPreferencesScore(list: List<Int>){
        preferences.edit()
            .putInt(BEST_5,list[0])
            .putInt(BEST_4,list[1])
            .putInt(BEST_3,list[2])
            .putInt(BEST_2,list[3])
            .putInt(BEST_1,list[4])
            .apply()
    }

    fun clearPreferences(){
        preferences.edit().clear().apply()
    }
}
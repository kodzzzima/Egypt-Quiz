package com.example.simple_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simple_game.util.AppPreference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppPreference.getPreference(this)
    }
}
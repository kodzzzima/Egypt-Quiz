package com.example.simple_game.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simple_game.util.AppPreference
import com.example.simple_game.util.Constants
import kotlin.properties.Delegates

class MainFragmentViewModel : ViewModel() {

    private val question = MutableLiveData<List<String>>()
    private lateinit var questionList: List<List<String>>

    var questionNumber by Delegates.notNull<Int>()
    var currentScore by Delegates.notNull<Int>()

    init {
        fetchQuestions()
    }

    fun fetchQuestions() {
        questionNumber = -1
        currentScore = 0
        questionList = getRandom10Questions(Constants.listAllQuestions)

        getNextQuestion()
    }

    private fun getRandom10Questions(list: List<List<String>>): List<List<String>> =
        list.shuffled().take(10)

    fun getNextQuestion() {
        questionNumber += 1
        try {
            question.postValue(questionList[questionNumber])
        } catch (e: Exception) {
            Log.d("testLog", e.toString())
        }
    }

    fun getQuestion(): MutableLiveData<List<String>> {
        return question
    }

    fun setScoreListToPreferences() {
        val scoreList = AppPreference.getPreferencesScore() as MutableList

        scoreList[0] = currentScore
        scoreList.sort()

        AppPreference.setPreferencesScore(scoreList.takeLast(5))
    }


}
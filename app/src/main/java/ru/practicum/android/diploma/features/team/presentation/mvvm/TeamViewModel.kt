package ru.practicum.android.diploma.features.team.presentation.mvvm

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamViewModel : ViewModel() {
    private val stateLiveData = MutableLiveData<Boolean>()
    fun observeState(): LiveData<Boolean> = stateLiveData

    private val stateLiveScore = MutableLiveData<Int>()
    fun observeStateScore(): LiveData<Int> = stateLiveScore

    var isDarkMode: Boolean = false

    var score = 0

    init {
        val mode = AppCompatDelegate.getDefaultNightMode()
        isDarkMode = mode == 1
        stateLiveData.postValue(isDarkMode)
    }

    fun changeScore() {
        score++
        stateLiveScore.postValue(score)
    }

    fun changeTheme(value: Boolean) {
        if (value) {
            stateLiveData.postValue(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            stateLiveData.postValue(false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}

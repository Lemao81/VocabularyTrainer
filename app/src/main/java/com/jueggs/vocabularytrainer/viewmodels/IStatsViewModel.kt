package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData

interface IStatsViewModel {
    val stats: MutableList<MutableLiveData<String>>
}
package com.jueggs.vocabularytrainer.viewmodels.interfaces

import androidx.lifecycle.MutableLiveData

interface IStatsViewModel {
    val stats: MutableList<MutableLiveData<String>>
}
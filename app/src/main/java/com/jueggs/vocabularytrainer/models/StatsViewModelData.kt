package com.jueggs.vocabularytrainer.models

import androidx.lifecycle.MutableLiveData

class StatsViewModelData(
    val count: MutableLiveData<String> = MutableLiveData(),
    val isBold: MutableLiveData<Boolean> = MutableLiveData()
)
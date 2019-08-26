package com.jueggs.common.interfaces

import androidx.lifecycle.MutableLiveData

interface StatsViewModel {
    val stats: MutableList<MutableLiveData<String>>
}
package com.jueggs.common.interfaces

import androidx.lifecycle.MutableLiveData

interface IStatsViewModel {
    val stats: MutableList<MutableLiveData<String>>
}
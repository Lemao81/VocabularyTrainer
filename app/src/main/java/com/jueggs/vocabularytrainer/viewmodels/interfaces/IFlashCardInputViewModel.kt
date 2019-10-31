package com.jueggs.vocabularytrainer.viewmodels.interfaces

import androidx.lifecycle.MutableLiveData

interface IFlashCardInputViewModel {
    val frontSideText: MutableLiveData<String>
    val backSideTexts: List<MutableLiveData<String>>
}
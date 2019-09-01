package com.jueggs.vocabularytrainer.models

data class FlashCardInputData(
    val frontSideText: String,
    val backSideTexts: List<String>
)
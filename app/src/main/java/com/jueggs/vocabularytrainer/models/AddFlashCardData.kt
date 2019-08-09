package com.jueggs.vocabularytrainer.models

data class AddFlashCardData(
    val frontSideText: String,
    val backSideTexts: List<String>
)
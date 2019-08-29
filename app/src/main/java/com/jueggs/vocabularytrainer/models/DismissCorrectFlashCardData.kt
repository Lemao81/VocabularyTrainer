package com.jueggs.vocabularytrainer.models

data class DismissCorrectFlashCardData(
    val currentFlashCardId: Long?,
    val cardLearnedMessageFormat: String
)
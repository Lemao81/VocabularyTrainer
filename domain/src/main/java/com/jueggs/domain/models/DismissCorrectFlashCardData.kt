package com.jueggs.domain.models

data class DismissCorrectFlashCardData(
    val currentFlashCardId: Long?,
    val cardLearnedMessageFormat: String
)
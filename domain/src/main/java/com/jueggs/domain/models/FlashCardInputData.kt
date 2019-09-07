package com.jueggs.domain.models

data class FlashCardInputData(
    val frontSideText: String,
    val backSideTexts: List<String>
)
package com.jueggs.vocabularytrainer.models

import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel

data class AddFlashCardData(
    val frontSideText: String,
    val backSideTexts: List<String>,
    val addFlashCardViewModel: AddFlashCardViewModel
)
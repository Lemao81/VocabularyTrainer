package com.jueggs.domain.models

sealed class FlashCardInputValidationResult

object Valid : FlashCardInputValidationResult()

object BlankFrontSide : FlashCardInputValidationResult()

object BlankBackSide : FlashCardInputValidationResult()
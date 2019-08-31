package com.jueggs.vocabularytrainer.validators

import com.jueggs.andutils.result.Invalid
import com.jueggs.andutils.result.Valid
import com.jueggs.andutils.result.ValidationResult
import com.jueggs.andutils.usecase.IValidator
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.models.AddFlashCardData

class AddFlashCardInputValidator : IValidator<AddFlashCardData> {
    override suspend fun invoke(data: AddFlashCardData): ValidationResult {
        return when {
            data.frontSideText.isBlank() -> Invalid(R.string.message_enter_front_side)
            data.backSideTexts.all { it.isBlank() } -> Invalid(R.string.message_enter_back_side)
            else -> Valid
        }
    }
}
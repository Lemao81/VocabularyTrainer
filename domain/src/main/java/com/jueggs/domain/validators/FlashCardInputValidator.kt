package com.jueggs.domain.validators

import com.jueggs.domain.models.BlankBackSide
import com.jueggs.domain.models.BlankFrontSide
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid
import com.jueggs.jutils.validation.IValidator

class FlashCardInputValidator : IValidator<FlashCardInputData, FlashCardInputValidationResult> {
    override suspend fun invoke(data: FlashCardInputData): FlashCardInputValidationResult {
        return when {
            data.frontSideText.isBlank() -> BlankFrontSide
            data.backSideTexts.all { it.isBlank() } -> BlankBackSide
            else -> Valid
        }
    }
}
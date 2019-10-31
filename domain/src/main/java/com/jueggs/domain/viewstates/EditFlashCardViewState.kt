package com.jueggs.domain.viewstates

import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid

data class EditFlashCardViewState(
    val inputValidationResult: FlashCardInputValidationResult = Valid,
    val isShouldPopFragment: Boolean = false,
    val isShouldMessageCardUpdated: Boolean = false,
    val isShouldClearFocus: Boolean = false,
    val frontSideText: String? = null,
    val backSideText1: String? = null,
    val backSideText2: String? = null,
    val backSideText3: String? = null,
    val backSideText4: String? = null
)
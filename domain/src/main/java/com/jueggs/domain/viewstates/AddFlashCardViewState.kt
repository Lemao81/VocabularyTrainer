package com.jueggs.domain.viewstates

import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid

data class AddFlashCardViewState(
    val inputValidationResult: FlashCardInputValidationResult = Valid,
    val isShouldPopFragment: Boolean = false,
    val isShouldEmptyInputs: Boolean = false,
    val isShouldFocusFrontSideEdit: Boolean = false,
    val isShouldClearFocus: Boolean = false,
    val isEditing: Boolean = false,
    val isShouldMessageCardAdded: Boolean = false,
    val isShouldMessageCardUpdated: Boolean = false,
    val frontSideText: String? = null,
    val backSideText1: String? = null,
    val backSideText2: String? = null,
    val backSideText3: String? = null,
    val backSideText4: String? = null
)
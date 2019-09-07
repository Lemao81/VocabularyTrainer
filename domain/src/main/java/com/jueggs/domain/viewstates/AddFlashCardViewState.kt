package com.jueggs.domain.viewstates

import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.jutils.INVALID

data class AddFlashCardViewState(
    val inputValidationResult: FlashCardInputValidationResult? = null,
    val isShouldPopFragment: Boolean = false,
    val isShouldEmptyInputs: Boolean = false,
    val isShouldFocusFrontSideEdit: Boolean = false,
    val isShouldClearFocus: Boolean = false,
    val isEditing: Boolean = false,
    val isShouldMessageCardAdded: Boolean = false,
    val isShouldMessageCardUpdated: Boolean = false,
    val backSideViewsShownUpToIndex: Int = 0,
    val focusedInputIndex: Int = INVALID,
    val frontSideText: String? = null,
    val backSideText1: String? = null,
    val backSideText2: String? = null,
    val backSideText3: String? = null,
    val backSideText4: String? = null,
    val backSideText5: String? = null
)
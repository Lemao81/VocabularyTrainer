package com.jueggs.vocabularytrainer.viewstates

import com.jueggs.jutils.INVALID

data class AddFlashCardViewState(
    val navigationId: Int? = null,
    val shortMessageId: Int? = null,
    val longMessageId: Int? = null,
    val isShouldPopFragment: Boolean = false,
    val isShouldEmptyInputs: Boolean = false,
    val isShouldFocusFrontSideEdit: Boolean = false,
    val backSideViewsShownUpToIndex: Int = 0,
    val focusedInputIndex: Int = INVALID,
    val isEditing: Boolean = false,
    val frontSideText: String? = null,
    val backSideText1: String? = null,
    val backSideText2: String? = null,
    val backSideText3: String? = null,
    val backSideText4: String? = null,
    val backSideText5: String? = null
)
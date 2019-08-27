package com.jueggs.vocabularytrainer.viewstates

import com.jueggs.jutils.INVALID

data class AddFlashCardViewState(
    val navigationId: Int? = null,
    val shortMessageId: Int? = null,
    val longMessageId: Int? = null,
    val isShouldPopFragment: Boolean = false,
    val isShouldEmptyInputs: Boolean = false,
    val backSideViewsShownUpToIndex: Int = 0,
    val focusedInputIndex: Int = INVALID
)
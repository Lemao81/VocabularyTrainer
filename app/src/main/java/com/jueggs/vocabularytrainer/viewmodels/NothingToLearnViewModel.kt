package com.jueggs.vocabularytrainer.viewmodels

import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.NothingToLearnViewState

class NothingToLearnViewModel : BaseViewModel<NothingToLearnViewState>(NothingToLearnViewState()) {
    fun closeApp() = viewStateStore.dispatch(Trigger { copy(isShouldCloseApp = true) })

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(navigationId = R.id.action_nothingToLearnFragment_to_addFlashCardFragment) })
}
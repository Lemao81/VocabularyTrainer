package com.jueggs.vocabularytrainer.viewmodels

import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.vocabularytrainer.viewstates.NothingToLearnViewState

class NothingToLearnViewModel : BaseViewModel<NothingToLearnViewState>(NothingToLearnViewState()) {
    fun closeApp() = viewStateStore.dispatchAction(Trigger { copy(isShouldCloseApp = true) })
}
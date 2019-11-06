package com.jueggs.vocabularytrainer.viewmodels

import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.domain.usecases.CheckSomethingToLearnUseCase
import com.jueggs.domain.viewstates.SplashScreenViewState
import com.jueggs.vocabularytrainer.App

class SplashScreenViewModel(
    private val checkSomethingToLearnUseCase: CheckSomethingToLearnUseCase
) : BaseViewModel<SplashScreenViewState>(SplashScreenViewState(), App.isDev) {
    fun checkSomethingToLearn() = viewStateStore.dispatch(checkSomethingToLearnUseCase())
}
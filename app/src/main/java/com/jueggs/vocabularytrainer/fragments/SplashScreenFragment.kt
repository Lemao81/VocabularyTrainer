package com.jueggs.vocabularytrainer.fragments

import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment(true) {
    val viewModel by viewModel<SplashScreenViewModel>()

    override fun layout() = R.layout.fragment_splash_screen
}
package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<SplashScreenViewModel>()

    override fun layout() = R.layout.fragment_splash_screen

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
        }
    }
}
package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.SplashScreenViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<SplashScreenViewModel>()

    override fun layout() = R.layout.fragment_splash_screen
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            if (isShouldNavigateToLearnFragment) {
                navController?.navigate(R.id.action_splashScreenFragment_to_learnFragment)
            }
            if (isShouldNavigateToNothingToLearnFragment) {
                navController?.navigate(R.id.action_splashScreenFragment_to_nothingToLearnFragment)
            }
        }
    }

    override fun onStandby() {
        viewModel.checkSomethingToLearn()
    }
}
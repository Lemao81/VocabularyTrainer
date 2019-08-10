package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.goneOrVisible
import com.jueggs.andutils.extension.visibleOrGone
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.serialization.ImplicitReflectionSerializer
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearnFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<LearnViewModel>()

    override fun layout() = R.layout.fragment_learn

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText?.let { viewModel.backSideText.postValue(it) }
            btnReveal.goneOrVisible = isRevealed
            btnWrong.visibleOrGone = isRevealed
            btnCorrect.visibleOrGone = isRevealed
            txtBackSideText.visibleOrGone = isRevealed
        }
    }

    @ImplicitReflectionSerializer
    override fun onStandby() {
        viewModel.showNextFlashCard()
    }
}
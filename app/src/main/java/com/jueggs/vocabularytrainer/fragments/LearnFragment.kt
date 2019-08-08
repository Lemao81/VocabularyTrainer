package com.jueggs.vocabularytrainer.fragments

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
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
            btnReveal.visibility = if (isRevealed) View.GONE else View.VISIBLE
            btnWrong.visibility = if (isRevealed) View.VISIBLE else View.GONE
            btnCorrect.visibility = if (isRevealed) View.VISIBLE else View.GONE
            txtBackSideText.visibility = if (isRevealed) View.VISIBLE else View.GONE
        }
    }

    @ImplicitReflectionSerializer
    override fun onStandby() {
        viewModel.showNextFlashCard()
    }
}
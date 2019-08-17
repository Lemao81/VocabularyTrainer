package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.invisibleOrVisible
import com.jueggs.andutils.extension.longToast
import com.jueggs.andutils.extension.shortToast
import com.jueggs.andutils.extension.visibleOrInvisible
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import kotlinx.android.synthetic.main.fragment_learn.*
import kotlinx.serialization.ImplicitReflectionSerializer
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearnFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<LearnViewModel>()

    override fun layout() = R.layout.fragment_learn
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
            shortMessageId?.let { shortToast(it) }
            longMessage?.let { longToast(it) }
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText?.let { viewModel.backSideText.postValue(it) }
            btnReveal.invisibleOrVisible = isRevealed
            fabWrong.visibleOrInvisible = isRevealed
            fabCorrect.visibleOrInvisible = isRevealed
            txtBackSideText.visibleOrInvisible = isRevealed
        }
    }

    @ImplicitReflectionSerializer
    override fun onStandby() {
        viewModel.showNextFlashCard()
    }
}
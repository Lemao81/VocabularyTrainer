package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseNavigationFragment
import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NothingToLearnFragment : BaseNavigationFragment() {
    val viewModel by viewModel<NothingToLearnViewModel>()

    override fun layout() = R.layout.fragment_nothing_to_learn
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(owner) {
            if (isShouldNavigateToAddFlashCard) {
                navController.navigate(R.id.action_nothingToLearnFragment_to_addFlashCardFragment)
            }
            if (isShouldCloseApp) {
                requireActivity().finishAndRemoveTask()
            }
            viewModel.stats[FlashCardBox.ONE.index].postValue(stats1.toString())
            viewModel.stats[FlashCardBox.TWO.index].postValue(stats2.toString())
            viewModel.stats[FlashCardBox.THREE.index].postValue(stats3.toString())
            viewModel.stats[FlashCardBox.FOUR.index].postValue(stats4.toString())
            viewModel.stats[FlashCardBox.FIVE.index].postValue(stats5.toString())
            viewModel.stats[FlashCardBox.SIX.index].postValue(stats6.toString())
        }
    }

    override fun onStandby() {
        viewModel.updateStats()
    }
}
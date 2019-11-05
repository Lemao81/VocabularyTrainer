package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseNavigationFragment
import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.models.StatsData
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import kotlinx.android.synthetic.main.fragment_nothing_to_learn.*
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
            postStats(FlashCardBox.ONE, stats1)
            postStats(FlashCardBox.TWO, stats2)
            postStats(FlashCardBox.THREE, stats3)
            postStats(FlashCardBox.FOUR, stats4)
            postStats(FlashCardBox.FIVE, stats5)
            postStats(FlashCardBox.SIX, stats6)
            fabMenu.open(true)
        }
    }

    override fun onStandby() {
        viewModel.updateStats()
    }

    private fun postStats(box: FlashCardBox, data: StatsData) {
        viewModel.stats[box.index].apply {
            count.postValue(data.count.toString())
            isBold.postValue(data.isHasDueCards)
        }
    }
}
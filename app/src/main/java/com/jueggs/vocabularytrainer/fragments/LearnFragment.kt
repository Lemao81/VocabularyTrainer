package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.*
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.jutils.INVALIDL
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import kotlinx.android.synthetic.main.fragment_learn.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearnFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<LearnViewModel>()

    override fun layout() = R.layout.fragment_learn
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
            if (isShouldNavigateToFlashCardEditing) {
                val navDirection = LearnFragmentDirections.actionLearnFragmentToAddFlashCardFragment(viewModel.currentFlashCardId ?: INVALIDL)
                navController?.navigate(navDirection)
            }
            shortMessageId?.let { shortToast(it) }
            longMessage?.let { longToast(it) }
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText?.let { viewModel.backSideText.postValue(it) }
            boxNumber?.let { viewModel.boxNumber.postValue(it.toString()) }
            viewModel.currentFlashCardId = currentFlashCardId
            fabWrong.visibleOrInvisible = isRevealed
            fabCorrect.visibleOrInvisible = isRevealed
            txtFrontSideText.invisibleOrVisible = isRevealed
            txtBackSideText.visibleOrInvisible = isRevealed
            cardFlashCard.isClickable = !isRevealed
            context?.let { cardFlashCard.setCardBackgroundColor(it.colorResToInt(cardBackgroundColorId ?: R.color.box1_background)) }
            if (isShouldShowRemoveFlashCardConfirmation) {
                showConfirmDialog(R.string.dialog_remove_flashcard_title, R.string.dialog_remove_flashcard_message, viewModel::removeFlashCard, viewModel::cancelFlashCardRemoval)
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
        viewModel.showNextFlashCard()
        viewModel.updateStats()
    }
}
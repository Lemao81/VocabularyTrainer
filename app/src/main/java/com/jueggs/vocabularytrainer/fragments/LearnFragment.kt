package com.jueggs.vocabularytrainer.fragments

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.colorResToInt
import com.jueggs.andutils.extension.invisibleOrVisible
import com.jueggs.andutils.extension.longToast
import com.jueggs.andutils.extension.shortToast
import com.jueggs.andutils.extension.showConfirmDialog
import com.jueggs.andutils.extension.visibleOrInvisible
import com.jueggs.domain.enums.FlashCardBox
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
            when {
                isShouldNavigateToNothingToLearn -> navController?.navigate(R.id.action_learnFragment_to_nothingToLearnFragment)
                isShouldNavigateToAddFlashCard -> navController?.navigate(R.id.action_learnFragment_to_addFlashCardFragment)
                isShouldNavigateToEditFlashCard -> {
                    val navDirection = LearnFragmentDirections.actionLearnFragmentToAddFlashCardFragment(viewModel.currentFlashCardId ?: INVALIDL)
                    navController?.navigate(navDirection)
                }
                isShouldMessageCardRemoved -> shortToast(R.string.message_card_removed)
                isShouldShowRemoveFlashCardConfirmation -> {
                    showConfirmDialog(R.string.dialog_remove_flashcard_title, R.string.dialog_remove_flashcard_message, viewModel::removeFlashCard, viewModel::cancelFlashCardRemoval)
                }
            }
            longMessage?.let { longToast(it) }
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText?.let { viewModel.backSideText.postValue(it) }
            nextShownFlashCardBox?.let { box ->
                viewModel.boxNumber.postValue(box.number.toString())
                context?.let { cardFlashCard.setCardBackgroundColor(mapFlashCardBoxToColorInt(box, it)) }
            }
            viewModel.currentFlashCardId = currentFlashCardId
            fabWrong.visibleOrInvisible = isRevealed
            fabCorrect.visibleOrInvisible = isRevealed
            txtFrontSideText.invisibleOrVisible = isRevealed
            txtBackSideText.visibleOrInvisible = isRevealed
            cardFlashCard.isClickable = !isRevealed
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

    private fun mapFlashCardBoxToColorInt(box: FlashCardBox, context: Context): Int {
        val colorId = when (box) {
            FlashCardBox.TWO -> R.color.box2_background
            FlashCardBox.THREE -> R.color.box3_background
            FlashCardBox.FOUR -> R.color.box4_background
            FlashCardBox.FIVE -> R.color.box5_background
            FlashCardBox.SIX -> R.color.box6_background
            else -> R.color.box1_background
        }

        return context.colorResToInt(colorId)
    }
}
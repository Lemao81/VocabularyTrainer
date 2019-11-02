package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.jueggs.andutils.base.BaseNavigationFragment
import com.jueggs.andutils.extension.hideKeyboard
import com.jueggs.andutils.extension.longToast
import com.jueggs.andutils.extension.onEditDone
import com.jueggs.andutils.extension.shortToast
import com.jueggs.domain.models.BlankBackSide
import com.jueggs.domain.models.BlankFrontSide
import com.jueggs.domain.models.Valid
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.EditFlashCardViewModel
import kotlinx.android.synthetic.main.include_merge_flashcard_input.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditFlashCardFragment : BaseNavigationFragment() {
    val viewModel by viewModel<EditFlashCardViewModel>()
    val navArgs: EditFlashCardFragmentArgs by navArgs()

    override fun layout() = R.layout.fragment_edit_flash_card
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(owner) {
            if (isShouldPopFragment) {
                navController.popBackStack()
            }
            when (inputValidationResult) {
                BlankFrontSide -> R.string.message_enter_front_side
                BlankBackSide -> R.string.message_enter_back_side
                Valid -> null
            }?.let { longToast(it) }
            if (isShouldMessageCardUpdated) {
                shortToast(R.string.message_card_updated)
            }
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText1?.let { viewModel.backSideTexts[0].postValue(it) }
            backSideText2?.let { viewModel.backSideTexts[1].postValue(it) }
            backSideText3?.let { viewModel.backSideTexts[2].postValue(it) }
        }
    }

    override fun setListeners() = edtBackSide3.onEditDone {
        viewModel.updateFlashCard()
        hideKeyboard()
    }

    override fun onStandby() = viewModel.loadFlashCardForEditing(navArgs.flashCardId)
}
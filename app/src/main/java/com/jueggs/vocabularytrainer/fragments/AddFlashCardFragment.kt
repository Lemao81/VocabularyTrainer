package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseNavigationFragment
import com.jueggs.andutils.extension.hideKeyboard
import com.jueggs.andutils.extension.longToast
import com.jueggs.andutils.extension.onEditDone
import com.jueggs.andutils.extension.shortToast
import com.jueggs.andutils.extension.showKeyboard
import com.jueggs.domain.models.BlankBackSide
import com.jueggs.domain.models.BlankFrontSide
import com.jueggs.domain.models.Valid
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import kotlinx.android.synthetic.main.fragment_add_flash_card.*
import kotlinx.android.synthetic.main.include_merge_flashcard_input.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFlashCardFragment : BaseNavigationFragment() {
    val viewModel by viewModel<AddFlashCardViewModel>()

    override fun layout() = R.layout.fragment_add_flash_card
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
            if (isShouldEmptyInputs) {
                viewModel.frontSideText.postValue("")
                viewModel.backSideTexts.forEach { it.postValue("") }
            }
            if (isShouldFocusFrontSideEdit) {
                edtFrontSide.requestFocus()
                showKeyboard()
            }
            if (isShouldClearFocus) {
                conRoot.requestFocus()
            }
            if (isShouldMessageCardAdded) {
                shortToast(R.string.message_card_added)
            }
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText1?.let { viewModel.backSideTexts[0].postValue(it) }
            backSideText2?.let { viewModel.backSideTexts[1].postValue(it) }
            backSideText3?.let { viewModel.backSideTexts[2].postValue(it) }
        }
    }

    override fun setListeners() = edtBackSide3.onEditDone {
        viewModel.addFlashCard()
        hideKeyboard()
    }

    override fun onStandby() = viewModel.focusFrontSideEdit()
}
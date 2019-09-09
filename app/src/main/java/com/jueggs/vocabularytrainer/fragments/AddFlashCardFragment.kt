package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.goneOrVisible
import com.jueggs.andutils.extension.invisibleOrVisible
import com.jueggs.andutils.extension.shortToast
import com.jueggs.andutils.extension.showKeyboard
import com.jueggs.andutils.extension.visibleOrInvisible
import com.jueggs.jutils.INVALIDL
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import kotlinx.android.synthetic.main.fragment_add_flash_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFlashCardFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<AddFlashCardViewModel>()
    val navArgs: AddFlashCardFragmentArgs by navArgs()

    override fun layout() = R.layout.fragment_add_flash_card
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            when {
                isShouldPopFragment -> navController?.popBackStack()
                isShouldEmptyInputs -> {
                    viewModel.frontSideText.postValue("")
                    viewModel.backSideTexts.forEach { it.postValue("") }
                }
                isShouldFocusFrontSideEdit -> {
                    edtFrontSide.requestFocus()
                    showKeyboard()
                }
                isShouldClearFocus -> conRoot.requestFocus()
                isShouldMessageCardAdded -> shortToast(R.string.message_card_added)
                isShouldMessageCardUpdated -> shortToast(R.string.message_card_updated)
            }
            btnAddCard.invisibleOrVisible = isEditing
            btnSaveCard.visibleOrInvisible = isEditing
            swtKeepAdding.goneOrVisible = isEditing
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText1?.let { viewModel.backSideTexts[0].postValue(it) }
            backSideText2?.let { viewModel.backSideTexts[1].postValue(it) }
            backSideText3?.let { viewModel.backSideTexts[2].postValue(it) }
            backSideText4?.let { viewModel.backSideTexts[3].postValue(it) }
        }
    }

    override fun onStandby() {
        if (navArgs.flashCardId != INVALIDL) {
            viewModel.loadFlashCardForEditing(navArgs.flashCardId)
        } else {
            viewModel.focusFrontSideEdit()
        }
    }
}
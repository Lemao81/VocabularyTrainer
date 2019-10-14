package com.jueggs.vocabularytrainer.fragments

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.goneOrVisible
import com.jueggs.andutils.extension.hideKeyboard
import com.jueggs.andutils.extension.invisibleOrVisible
import com.jueggs.andutils.extension.shortToast
import com.jueggs.andutils.extension.showKeyboard
import com.jueggs.andutils.extension.visibleOrInvisible
import com.jueggs.jutils.INVALIDL
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import kotlinx.android.synthetic.main.fragment_add_flash_card.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class AddFlashCardFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<AddFlashCardViewModel>()
    val navArgs: AddFlashCardFragmentArgs by navArgs()

    override fun layout() = R.layout.fragment_add_flash_card
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            if (isShouldPopFragment) {
                navController?.popBackStack()
            }
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
            if (isShouldMessageCardUpdated) {
                shortToast(R.string.message_card_updated)
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

    override fun setListeners() = edtBackSide3.onEditDone {
        viewModel.addFlashCard()
        activity?.hideKeyboard()
    }

    override fun onStandby() = if (navArgs.flashCardId != INVALIDL) {
        viewModel.loadFlashCardForEditing(navArgs.flashCardId)
    } else {
        viewModel.focusFrontSideEdit()
    }
}

// TODO lib
fun TextView.onEditDone(
    context: CoroutineContext = Dispatchers.Main,
    handler: suspend CoroutineScope.() -> Unit
) {
    setOnEditorActionListener { view, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            GlobalScope.launch(context, CoroutineStart.DEFAULT) {
                handler()
            }

            return@setOnEditorActionListener true
        }

        return@setOnEditorActionListener false
    }
}
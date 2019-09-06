package com.jueggs.vocabularytrainer.fragments

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.*
import com.jueggs.jutils.INVALID
import com.jueggs.jutils.INVALIDL
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import kotlinx.android.synthetic.main.fragment_add_flash_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFlashCardFragment : BaseFragment(isShouldSearchNavController = true) {
    private val backSideViews = mutableListOf<List<View>>()
    val viewModel by viewModel<AddFlashCardViewModel>()
    val navArgs: AddFlashCardFragmentArgs by navArgs()

    override fun layout() = R.layout.fragment_add_flash_card
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
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
            shortMessageId?.let { shortToast(it) }
            longMessageId?.let { longToast(it) }
            backSideViews.forEachIndexed { index, views ->
                when {
                    index < backSideViewsShownUpToIndex -> {
                        views.first { it is EditText }.makeVisible()
                        views.filter { it is FloatingActionButton }.forEach { it.makeInvisible() }
                    }
                    index == backSideViewsShownUpToIndex -> {
                        views.forEach { it.makeVisible() }
                    }
                    index > backSideViewsShownUpToIndex -> {
                        views.forEach { it.makeInvisible() }
                        viewModel.backSideTexts[index].postValue("")
                    }
                }
            }
            if (focusedInputIndex != INVALID) {
                backSideViews[focusedInputIndex].first { it is EditText }.requestFocus()
            }
            btnAddCard.invisibleOrVisible = isEditing
            btnSaveCard.visibleOrInvisible = isEditing
            swtKeepAdding.goneOrVisible = isEditing
            frontSideText?.let { viewModel.frontSideText.postValue(it) }
            backSideText1?.let { viewModel.backSideTexts[0].postValue(it) }
            backSideText2?.let { viewModel.backSideTexts[1].postValue(it) }
            backSideText3?.let { viewModel.backSideTexts[2].postValue(it) }
            backSideText4?.let { viewModel.backSideTexts[3].postValue(it) }
            backSideText5?.let { viewModel.backSideTexts[4].postValue(it) }
        }
    }

    override fun onStandby() {
        if (navArgs.flashCardId != INVALIDL) {
            viewModel.loadFlashCardForEditing(navArgs.flashCardId)
        } else {
            viewModel.focusFrontSideEdit()
        }
        backSideViews.add(listOf(edtBackSide1, fabShowBackSideInput1))
        backSideViews.add(listOf(edtBackSide2, fabShowBackSideInput2, fabHideBackSideInput2))
        backSideViews.add(listOf(edtBackSide3, fabShowBackSideInput3, fabHideBackSideInput3))
        backSideViews.add(listOf(edtBackSide4, fabShowBackSideInput4, fabHideBackSideInput4))
        backSideViews.add(listOf(edtBackSide5, fabHideBackSideInput5))
    }
}
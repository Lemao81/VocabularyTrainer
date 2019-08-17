package com.jueggs.vocabularytrainer.fragments

import android.view.View
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.andutils.extension.longToast
import com.jueggs.andutils.extension.makeInvisible
import com.jueggs.andutils.extension.makeVisible
import com.jueggs.andutils.extension.shortToast
import com.jueggs.jutils.INVALID
import com.jueggs.vocabularytrainer.BR
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import kotlinx.android.synthetic.main.fragment_add_flash_card.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFlashCardFragment : BaseFragment(isShouldSearchNavController = true) {
    private val backSideViews = mutableListOf<List<View>>()
    val viewModel by viewModel<AddFlashCardViewModel>()

    override fun layout() = R.layout.fragment_add_flash_card
    override fun bindingItems() = mapOf(BR.viewModel to viewModel)

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
            if (isShouldPopFragment) {
                navController?.popBackStack()
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
            } else {
                edtFrontSide.requestFocus()
            }
        }
    }

    override fun onStandby() {
        backSideViews.add(listOf(edtBackSide1, fabShowBackSideInput1))
        backSideViews.add(listOf(edtBackSide2, fabShowBackSideInput2, fabHideBackSideInput2))
        backSideViews.add(listOf(edtBackSide3, fabShowBackSideInput3, fabHideBackSideInput3))
        backSideViews.add(listOf(edtBackSide4, fabShowBackSideInput4, fabHideBackSideInput4))
        backSideViews.add(listOf(edtBackSide5, fabHideBackSideInput5))
    }
}
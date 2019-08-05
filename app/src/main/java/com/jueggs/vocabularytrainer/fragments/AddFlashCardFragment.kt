package com.jueggs.vocabularytrainer.fragments

import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFlashCardFragment : BaseFragment(true) {
    val viewModel by viewModel<AddFlashCardViewModel>()

    override fun layout() = R.layout.fragment_add_flash_card
}
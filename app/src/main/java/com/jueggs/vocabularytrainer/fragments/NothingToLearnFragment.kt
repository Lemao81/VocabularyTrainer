package com.jueggs.vocabularytrainer.fragments

import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NothingToLearnFragment : BaseFragment(true) {
    val viewModel by viewModel<NothingToLearnViewModel>()

    override fun layout() = R.layout.fragment_nothing_to_learn
}
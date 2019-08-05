package com.jueggs.vocabularytrainer.fragments

import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.LearnViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LearnFragment : BaseFragment(true) {
    val viewModel by viewModel<LearnViewModel>()

    override fun layout() = R.layout.fragment_learn
}
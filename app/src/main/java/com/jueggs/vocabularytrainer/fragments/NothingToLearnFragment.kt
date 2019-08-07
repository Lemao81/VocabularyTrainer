package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.NothingToLearnViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NothingToLearnFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<NothingToLearnViewModel>()

    override fun layout() = R.layout.fragment_nothing_to_learn

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
        }
    }
}
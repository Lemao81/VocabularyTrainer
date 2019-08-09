package com.jueggs.vocabularytrainer.fragments

import androidx.lifecycle.LifecycleOwner
import com.jueggs.andutils.base.BaseFragment
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewmodels.AddFlashCardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFlashCardFragment : BaseFragment(isShouldSearchNavController = true) {
    val viewModel by viewModel<AddFlashCardViewModel>()

    override fun layout() = R.layout.fragment_add_flash_card

    override fun observeLiveData(owner: LifecycleOwner) {
        viewModel.viewStateStore.observe(this) {
            navigationId?.let { navController?.navigate(it) }
            if (isShouldPopFragment) {
                navController?.popBackStack()
            }
        }
    }
}
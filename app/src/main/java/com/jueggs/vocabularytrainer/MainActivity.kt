package com.jueggs.vocabularytrainer

import android.view.View
import androidx.navigation.ui.AppBarConfiguration
import com.jueggs.andutils.base.BaseMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMainActivity() {
    override fun layout() = R.layout.activity_main
    override fun navHostFragment() = R.id.nav_host_fragment
    override fun appBarConfiguration() = AppBarConfiguration(setOf(R.id.splashScreenFragment, R.id.nothingToLearnFragment, R.id.learnFragment))
    override fun toolbar(): View? = toolbar
}
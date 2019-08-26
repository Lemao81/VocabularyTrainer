package com.jueggs.vocabularytrainer.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jueggs.andutils.aac.Alter
import com.jueggs.andutils.aac.BaseViewModel
import com.jueggs.andutils.aac.Trigger
import com.jueggs.common.enums.FlashCardBox
import com.jueggs.common.interfaces.StatsViewModel
import com.jueggs.database.repositories.interfaces.FlashCardRepository
import com.jueggs.vocabularytrainer.R
import com.jueggs.vocabularytrainer.viewstates.NothingToLearnViewState
import kotlinx.coroutines.launch

class NothingToLearnViewModel(
    private val flashCardRepository: FlashCardRepository
) : BaseViewModel<NothingToLearnViewState>(NothingToLearnViewState()), StatsViewModel {
    override val stats: MutableList<MutableLiveData<String>> = mutableListOf()

    init {
        repeat(6) {
            stats.add(MutableLiveData())
        }
    }

    fun closeApp() = viewStateStore.dispatch(Trigger { copy(isShouldCloseApp = true) })

    fun addFlashCard() = viewStateStore.dispatch(Trigger { copy(navigationId = R.id.action_nothingToLearnFragment_to_addFlashCardFragment) })

    fun updateStats() {
        launch {
            val flashCards = flashCardRepository.readAll()
            val groups = flashCards.groupBy { it.boxNumber }

            viewStateStore.dispatch(Alter {
                copy(
                    stats1 = groups[FlashCardBox.ONE.number]?.size ?: 0,
                    stats2 = groups[FlashCardBox.TWO.number]?.size ?: 0,
                    stats3 = groups[FlashCardBox.THREE.number]?.size ?: 0,
                    stats4 = groups[FlashCardBox.FOUR.number]?.size ?: 0,
                    stats5 = groups[FlashCardBox.FIVE.number]?.size ?: 0,
                    stats6 = groups[FlashCardBox.SIX.number]?.size ?: 0
                )
            })
        }
    }
}
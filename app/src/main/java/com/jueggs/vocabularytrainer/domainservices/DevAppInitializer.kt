package com.jueggs.vocabularytrainer.domainservices

import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.services.interfaces.IAppInitializer
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DevAppInitializer(
    private val flashCardRepository: IFlashCardRepository
) : IAppInitializer {

    override fun initialize() {
        GlobalScope.launch {
            if (flashCardRepository.readAll().isEmpty()) {
                flashCardRepository.insertAll(
                    listOf(
                        FlashCard(null, "this", listOf("dies", "das")),
                        FlashCard(null, "is", listOf("ist")),
                        FlashCard(null, "just", listOf("nur", "bloß")),
                        FlashCard(null, "some", listOf("einige", "irgendwelche")),
                        FlashCard(null, "useless", listOf("nutzlos")),
                        FlashCard(null, "initial", listOf("initial", "einleitend")),
                        FlashCard(null, "sample", listOf("Probe", "Übung", "Testdaten")),
                        FlashCard(null, "data", listOf("Daten")),
                        FlashCard(null, "for", listOf("für")),
                        FlashCard(null, "debug", listOf("Debug"))
                    )
                )
            }
        }
    }
}
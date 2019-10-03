package com.jueggs.domain.helper

import com.jueggs.domain.services.interfaces.IFlashCardBoxService
import com.jueggs.domain.services.interfaces.IFlashCardRepository
import com.jueggs.jutils.usecase.StateEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.channels.ProducerScope
import org.junit.jupiter.api.BeforeEach

abstract class AbstractUseCaseTest<TClassUnderTest : Any, TViewState> {
    lateinit var classUnderTest: TClassUnderTest
    lateinit var producerScopeMock: ProducerScope<StateEvent<TViewState>>
    lateinit var flashCardRepositoryMock: IFlashCardRepository
    lateinit var flashCardBoxService: IFlashCardBoxService
    lateinit var capturedStateEvents: MutableList<StateEvent<TViewState>>

    @BeforeEach
    fun setupAnnotated() {
        producerScopeMock = mockk()
        flashCardRepositoryMock = mockk()
        flashCardBoxService = mockk()
        capturedStateEvents = mutableListOf()
        coEvery { producerScopeMock.send(capture(capturedStateEvents)) } returns Unit
        setup()
    }

    open fun setup() {}
}
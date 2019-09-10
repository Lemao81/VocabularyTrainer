package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.interfaces.IFlashCardRepository
import com.jueggs.domain.models.AddFlashCardData
import com.jueggs.domain.models.BlankFrontSide
import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid
import com.jueggs.domain.viewstates.AddFlashCardViewState
import com.jueggs.jutils.usecase.StateEvent
import com.jueggs.jutils.usecase.Trigger
import com.jueggs.jutils.validation.IValidator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AddFlashCardUseCase")
internal class AddFlashCardUseCaseTest {
    private lateinit var _flashCardRepositoryMock: IFlashCardRepository
    private lateinit var _validatorMock: IValidator<FlashCardInputData, FlashCardInputValidationResult>
    private lateinit var _producerScopeMock: ProducerScope<StateEvent<AddFlashCardViewState>>
    private lateinit var _classUnderTest: AddFlashCardUseCase

    @Nested
    @DisplayName("Given input is valid")
    inner class InputValid {
        @BeforeEach
        fun setUp() {
            _flashCardRepositoryMock = mockk()
            _validatorMock = mockk()
            _producerScopeMock = mockk()
            _classUnderTest = AddFlashCardUseCase(_flashCardRepositoryMock, _validatorMock)
        }

        @Test
        @DisplayName("then new flashcard should be inserted properly (not keep adding)")
        fun `test that valid input inserts new flashcard properly (not keep adding)`() {
            // Arrange
            val backSideTexts = listOf("one", "two")
            val data = AddFlashCardData(false, FlashCardInputData("frontText", backSideTexts))
            val stateEvents = mutableListOf<StateEvent<AddFlashCardViewState>>()
            val flashCardSlot = slot<FlashCard>()
            coEvery { _validatorMock.invoke(any()) } returns Valid
            coEvery { _producerScopeMock.send(capture(stateEvents)) } returns Unit
            coEvery { _flashCardRepositoryMock.insert(capture(flashCardSlot)) } returns Unit

            // Act
            runBlocking {
                _classUnderTest(data).invoke(_producerScopeMock)
            }

            // Assert
            val flashCard = flashCardSlot.captured
            assertThat(stateEvents.size).isEqualTo(2)
            assertThat(stateEvents[0]).isInstanceOf(Trigger::class.java)
            assertThat(stateEvents[1]).isInstanceOf(Trigger::class.java)
            coVerify(exactly = 1) { _flashCardRepositoryMock.insert(any()) }
            assertThat(flashCard.id).isNull()
            assertThat(flashCard.box).isEqualTo(FlashCardBox.ONE)
            assertThat(flashCard.frontSideText).isEqualTo("frontText")
            assertThat(flashCard.backSideTexts).isSameAs(backSideTexts)
            val newState1 = stateEvents[0].action.invoke(AddFlashCardViewState())
            val newState2 = stateEvents[1].action.invoke(AddFlashCardViewState())
            assertThat(newState1.isShouldMessageCardAdded).isTrue()
            assertThat(newState2.isShouldPopFragment).isTrue()
        }

        @Test
        @DisplayName("then new flashcard should be inserted properly (keep adding)")
        fun `test that valid input inserts new flashcard properly (keep adding)`() {
            // Arrange
            val backSideTexts = listOf("one", "two")
            val data = AddFlashCardData(true, FlashCardInputData("frontText", backSideTexts))
            val stateEvents = mutableListOf<StateEvent<AddFlashCardViewState>>()
            val flashCardSlot = slot<FlashCard>()
            coEvery { _validatorMock.invoke(any()) } returns Valid
            coEvery { _producerScopeMock.send(capture(stateEvents)) } returns Unit
            coEvery { _flashCardRepositoryMock.insert(capture(flashCardSlot)) } returns Unit

            // Act
            runBlocking {
                _classUnderTest(data).invoke(_producerScopeMock)
            }

            // Assert
            val flashCard = flashCardSlot.captured
            assertThat(stateEvents.size).isEqualTo(2)
            assertThat(stateEvents[0]).isInstanceOf(Trigger::class.java)
            assertThat(stateEvents[1]).isInstanceOf(Trigger::class.java)
            coVerify(exactly = 1) { _flashCardRepositoryMock.insert(any()) }
            assertThat(flashCard.id).isNull()
            assertThat(flashCard.box).isEqualTo(FlashCardBox.ONE)
            assertThat(flashCard.frontSideText).isEqualTo("frontText")
            assertThat(flashCard.backSideTexts).isSameAs(backSideTexts)
            val newState1 = stateEvents[0].action.invoke(AddFlashCardViewState())
            val newState2 = stateEvents[1].action.invoke(AddFlashCardViewState())
            assertThat(newState1.isShouldMessageCardAdded).isTrue()
            assertThat(newState2.isShouldPopFragment).isFalse()
            assertThat(newState2.isShouldEmptyInputs).isTrue()
            assertThat(newState2.isShouldFocusFrontSideEdit).isTrue()
        }
    }

    @Nested
    @DisplayName("Given input is not valid")
    inner class InputInvalid {
        @BeforeEach
        fun setUp() {
            _flashCardRepositoryMock = mockk()
            _validatorMock = mockk()
            _producerScopeMock = mockk()
            _classUnderTest = AddFlashCardUseCase(_flashCardRepositoryMock, _validatorMock)
        }

        @Test
        @DisplayName("then validation result should be triggered")
        fun `test that invalid input triggers validation result`() {
            // Arrange
            val data = AddFlashCardData(null, FlashCardInputData("", emptyList()))
            val stateEventSlot = slot<StateEvent<AddFlashCardViewState>>()
            coEvery { _validatorMock.invoke(any()) } returns BlankFrontSide
            coEvery { _producerScopeMock.send(capture(stateEventSlot)) } returns Unit

            // Act
            runBlocking {
                _classUnderTest(data).invoke(_producerScopeMock)
            }

            // Assert
            val stateEvent = stateEventSlot.captured
            val newState = stateEvent.action.invoke(AddFlashCardViewState())
            assertThat(stateEvent).isInstanceOf(Trigger::class.java)
            assertThat(newState.inputValidationResult).isInstanceOf(BlankFrontSide::class.java)
            coVerify(exactly = 1) { _producerScopeMock.send(any()) }
            confirmVerified(_producerScopeMock)
        }
    }
}
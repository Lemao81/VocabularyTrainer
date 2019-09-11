package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.helper.AbstractUseCaseTest
import com.jueggs.domain.helper.AbstractUseCaseTestInnerClass
import com.jueggs.domain.models.AddFlashCardData
import com.jueggs.domain.models.BlankFrontSide
import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.models.FlashCardInputData
import com.jueggs.domain.models.FlashCardInputValidationResult
import com.jueggs.domain.models.Valid
import com.jueggs.domain.viewstates.AddFlashCardViewState
import com.jueggs.jutils.usecase.Trigger
import com.jueggs.jutils.validation.IValidator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("AddFlashCardUseCase")
internal class AddFlashCardUseCaseTest : AbstractUseCaseTest<AddFlashCardUseCase, AddFlashCardViewState>() {
    private lateinit var _validatorMock: IValidator<FlashCardInputData, FlashCardInputValidationResult>

    @Nested
    @DisplayName("Given input is valid")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class InputValid : AbstractUseCaseTestInnerClass<AddFlashCardUseCase, AddFlashCardViewState>(this@AddFlashCardUseCaseTest) {

        override fun setup() {
            _validatorMock = mockk()
            classUnderTest = AddFlashCardUseCase(flashCardRepositoryMock, _validatorMock)
        }

        @ParameterizedTest
        @MethodSource("provideKeepAddingNegatives")
        @DisplayName("then new flashcard should be inserted properly (not keep adding)")
        fun `test that valid input inserts new flashcard properly (not keep adding)`(isKeepAdding: Boolean?) {
            // Arrange
            val backSideTexts = listOf("one", "two")
            val data = AddFlashCardData(isKeepAdding, FlashCardInputData("frontText", backSideTexts))
            val flashCardSlot = slot<FlashCard>()
            coEvery { _validatorMock.invoke(any()) } returns Valid
            coEvery { flashCardRepositoryMock.insert(capture(flashCardSlot)) } returns Unit

            // Act
            runBlocking {
                classUnderTest(data).invoke(producerScopeMock)
            }

            // Assert
            val flashCard = flashCardSlot.captured
            assertThat(capturedStateEvents.size).isEqualTo(2)
            assertThat(capturedStateEvents[0]).isInstanceOf(Trigger::class.java)
            assertThat(capturedStateEvents[1]).isInstanceOf(Trigger::class.java)
            coVerify(exactly = 1) { flashCardRepositoryMock.insert(any()) }
            assertThat(flashCard.id).isNull()
            assertThat(flashCard.box).isEqualTo(FlashCardBox.ONE)
            assertThat(flashCard.frontSideText).isEqualTo("frontText")
            assertThat(flashCard.backSideTexts).isSameAs(backSideTexts)
            val newState1 = capturedStateEvents[0].action.invoke(AddFlashCardViewState())
            val newState2 = capturedStateEvents[1].action.invoke(AddFlashCardViewState())
            assertThat(newState1.isShouldMessageCardAdded).isTrue()
            assertThat(newState2.isShouldPopFragment).isTrue()
        }

        private fun provideKeepAddingNegatives(): List<Boolean?> = listOf(false, null)

        @Test
        @DisplayName("then new flashcard should be inserted properly (keep adding)")
        fun `test that valid input inserts new flashcard properly (keep adding)`() {
            // Arrange
            val backSideTexts = listOf("one", "two")
            val data = AddFlashCardData(true, FlashCardInputData("frontText", backSideTexts))
            val flashCardSlot = slot<FlashCard>()
            coEvery { _validatorMock.invoke(any()) } returns Valid
            coEvery { flashCardRepositoryMock.insert(capture(flashCardSlot)) } returns Unit

            // Act
            runBlocking {
                classUnderTest(data).invoke(producerScopeMock)
            }

            // Assert
            val flashCard = flashCardSlot.captured
            assertThat(capturedStateEvents.size).isEqualTo(2)
            assertThat(capturedStateEvents[0]).isInstanceOf(Trigger::class.java)
            assertThat(capturedStateEvents[1]).isInstanceOf(Trigger::class.java)
            coVerify(exactly = 1) { flashCardRepositoryMock.insert(any()) }
            assertThat(flashCard.id).isNull()
            assertThat(flashCard.box).isEqualTo(FlashCardBox.ONE)
            assertThat(flashCard.frontSideText).isEqualTo("frontText")
            assertThat(flashCard.backSideTexts).isSameAs(backSideTexts)
            val newState1 = capturedStateEvents[0].action.invoke(AddFlashCardViewState())
            val newState2 = capturedStateEvents[1].action.invoke(AddFlashCardViewState())
            assertThat(newState1.isShouldMessageCardAdded).isTrue()
            assertThat(newState2.isShouldPopFragment).isFalse()
            assertThat(newState2.isShouldEmptyInputs).isTrue()
            assertThat(newState2.isShouldFocusFrontSideEdit).isTrue()
        }
    }

    @Nested
    @DisplayName("Given input is not valid")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class InputInvalid : AbstractUseCaseTestInnerClass<AddFlashCardUseCase, AddFlashCardViewState>(this@AddFlashCardUseCaseTest) {

        fun setUp() {
            _validatorMock = mockk()
            classUnderTest = AddFlashCardUseCase(flashCardRepositoryMock, _validatorMock)
        }

        @Test
        @DisplayName("then validation result should be triggered")
        fun `test that invalid input triggers validation result`() {
            // Arrange
            val data = AddFlashCardData(null, FlashCardInputData("", emptyList()))
            coEvery { _validatorMock.invoke(any()) } returns BlankFrontSide

            // Act
            runBlocking {
                classUnderTest(data).invoke(producerScopeMock)
            }

            // Assert
            assertThat(capturedStateEvents.size).isEqualTo(1)
            val newState = capturedStateEvents[0].action.invoke(AddFlashCardViewState())
            assertThat(capturedStateEvents[0]).isInstanceOf(Trigger::class.java)
            assertThat(newState.inputValidationResult).isInstanceOf(BlankFrontSide::class.java)
            coVerify(exactly = 1) { producerScopeMock.send(any()) }
            confirmVerified(producerScopeMock)
        }
    }
}
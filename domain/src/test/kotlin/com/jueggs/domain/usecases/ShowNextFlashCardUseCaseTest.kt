package com.jueggs.domain.usecases

import com.jueggs.domain.enums.FlashCardBox
import com.jueggs.domain.helper.AbstractUseCaseTest
import com.jueggs.domain.helper.AbstractUseCaseTestInnerClass
import com.jueggs.domain.models.FlashCard
import com.jueggs.domain.viewstates.LearnViewState
import com.jueggs.jutils.usecase.Alter
import com.jueggs.jutils.usecase.Trigger
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ShowNextFlashCardUseCase")
internal class ShowNextFlashCardUseCaseTest : AbstractUseCaseTest<ShowNextFlashCardUseCase, LearnViewState>() {
    @Nested
    @DisplayName("Given no card to show is found")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class NotFindingCard : AbstractUseCaseTestInnerClass<ShowNextFlashCardUseCase, LearnViewState>(this@ShowNextFlashCardUseCaseTest) {

        override fun setup() {
            classUnderTest = ShowNextFlashCardUseCase(flashCardRepositoryMock, flashCardBoxService)
        }

        @Test
        @DisplayName("then trigger navigation and return")
        fun `test that not finding card triggers navigation`() {
            // Arrange
            coEvery { flashCardRepositoryMock.readByBoxAndExpiryDate(any(), any()) } returns emptyList()
            coEvery { flashCardBoxService.getBoxExpiryDate(any(), any()) } returns 0L

            // Act
            runBlocking {
                classUnderTest().invoke(producerScopeMock)
            }

            // Assert
            assertThat(capturedStateEvents.size).isEqualTo(1)
            assertThat(capturedStateEvents[0]).isInstanceOf(Trigger::class.java)
            assertThat(capturedStateEvents[0].action(LearnViewState()).isShouldNavigateToNothingToLearn).isTrue()
        }
    }

    @Nested
    @DisplayName("Given a card to show is found")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class FindingCard : AbstractUseCaseTestInnerClass<ShowNextFlashCardUseCase, LearnViewState>(this@ShowNextFlashCardUseCaseTest) {

        override fun setup() {
            classUnderTest = ShowNextFlashCardUseCase(flashCardRepositoryMock, flashCardBoxService)
        }

        @Test
        @DisplayName("then cards are found in correct box order")
        fun `test that cards are found in correct order`() {
            // Arrange
            val flashCardsBox3 = listOf(
                FlashCard(1, "any", listOf(), DateTime.now(), FlashCardBox.THREE),
                FlashCard(2, "any", listOf(), DateTime.now().minusDays(1), FlashCardBox.THREE)
            )
            coEvery { flashCardRepositoryMock.readByBoxAndExpiryDate(FlashCardBox.ONE, any()) } returns emptyList()
            coEvery { flashCardRepositoryMock.readByBoxAndExpiryDate(FlashCardBox.TWO, any()) } returns emptyList()
            coEvery { flashCardRepositoryMock.readByBoxAndExpiryDate(FlashCardBox.THREE, any()) } returns flashCardsBox3
            coEvery { flashCardBoxService.getBoxExpiryDate(any(), any()) } returns 0L

            // Act
            runBlocking {
                classUnderTest().invoke(producerScopeMock)
            }

            // Assert
            assertThat(capturedStateEvents.size).isEqualTo(1)
            assertThat(capturedStateEvents[0]).isInstanceOf(Alter::class.java)
            val viewState = capturedStateEvents[0].action(LearnViewState())
            assertThat(viewState.nextShownFlashCardBox).isNotNull
            assertThat(viewState.nextShownFlashCardBox!!.number).isEqualTo(FlashCardBox.THREE.number)
            assertThat(viewState.currentFlashCardId).isEqualTo(2)
        }

        @ParameterizedTest
        @DisplayName("then backside text should be formatted properly")
        @MethodSource("provideBacksideTextLists")
        fun `test that backside text is formatted properly`(backsideTexts: List<String>) {
            // Arrange
            val flashCardBox = listOf(FlashCard(1, "front side", backsideTexts, DateTime.now(), FlashCardBox.ONE))
            coEvery { flashCardRepositoryMock.readByBoxAndExpiryDate(FlashCardBox.ONE, any()) } returns flashCardBox
            coEvery { flashCardBoxService.getBoxExpiryDate(any(), any()) } returns 0L

            // Act
            runBlocking {
                classUnderTest().invoke(producerScopeMock)
            }

            // Assert
            assertThat(capturedStateEvents.size).isEqualTo(1)
            val viewState = capturedStateEvents[0].action(LearnViewState())
            if (backsideTexts.size == 1) {
                assertThat(viewState.backSideText).isEqualTo("one text")
            } else {
                assertThat(viewState.backSideText).isEqualTo("1.  first text${System.getProperty("line.separator")}2.  second text")
            }
            assertThat(viewState.frontSideText).isEqualTo("front side")
        }

        private fun provideBacksideTextLists() = listOf(listOf("one text"), listOf("first text", "second text"))
    }
}
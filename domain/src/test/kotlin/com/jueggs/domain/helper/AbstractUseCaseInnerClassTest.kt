package com.jueggs.domain.helper

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag

@Tag("usecase")
abstract class AbstractUseCaseTestInnerClass<TClassUnderTest : Any, TViewState>(
    private val nestingClass: AbstractUseCaseTest<TClassUnderTest, TViewState>
) {
    @BeforeEach
    fun setupAnnotated() {
        nestingClass.setupAnnotated()
        setup()
    }

    open fun setup() {}
}
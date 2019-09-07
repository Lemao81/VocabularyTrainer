package com.jueggs.domain.enums

enum class FlashCardBox(val number: Int, val latency: Int, val index: Int) {
    ONE(1, 1, 0),
    TWO(2, 2, 1),
    THREE(3, 4, 2),
    FOUR(4, 8, 3),
    FIVE(5, 16, 4),
    SIX(6, 32, 5)
}
package com.jueggs.common.enums

enum class FlashCardBox(val id: Int, val latency: Int) {
    ONE(1, 1),
    TWO(2, 2),
    THREE(3, 4),
    FOUR(4, 8),
    FIVE(5, 16),
    SIX(6, 32)
}
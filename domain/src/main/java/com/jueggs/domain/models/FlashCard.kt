package com.jueggs.domain.models

import com.jueggs.domain.enums.FlashCardBox
import org.joda.time.DateTime

class FlashCard(
    var id: Long? = null,
    var frontSideText: String = "",
    var backSideTexts: List<String> = mutableListOf(),
    var lastLearnedDate: DateTime = DateTime.now(),
    var box: FlashCardBox = FlashCardBox.ONE
)
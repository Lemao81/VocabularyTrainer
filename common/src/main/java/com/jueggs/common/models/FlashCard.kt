package com.jueggs.common.models

import com.jueggs.common.enums.FlashCardBox
import org.joda.time.DateTime

class FlashCard(
    var id: Long? = null,
    var frontSideText: String,
    var backSideTexts: List<String>,
    var lastLearnedDate: DateTime,
    var box: FlashCardBox
)
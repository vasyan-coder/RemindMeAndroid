package com.vasyancoder.remindme.ui.utils

import android.widget.Button

fun Button.getMeasuredWidthForNewText(newText: String): Int {
    val paint = this.paint
    val textWidth = paint.measureText(newText)
    val paddingStart = this.paddingStart
    val paddingEnd = this.paddingEnd
    return (textWidth + paddingStart + paddingEnd).toInt()
}
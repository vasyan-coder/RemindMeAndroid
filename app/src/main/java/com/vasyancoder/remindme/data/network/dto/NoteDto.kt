package com.vasyancoder.remindme.data.network.dto

import com.squareup.moshi.Json

data class NoteDto(
    val id: Long,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "body")
    val contentNote: String
)
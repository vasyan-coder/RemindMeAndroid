package com.vasyancoder.remindme.data.mapper

import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.data.network.dto.NoteDto

fun NoteDto.toNote() = Note(
    id = id,
    title = title,
    contentNote = contentNote
)
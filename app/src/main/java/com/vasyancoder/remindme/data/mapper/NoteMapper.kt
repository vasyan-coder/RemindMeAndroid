package com.vasyancoder.remindme.data.mapper

import com.vasyancoder.remindme.data.database.model.NoteDbModel
import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.data.network.dto.NoteDto

fun NoteDto.toNote() = Note(
    id = id,
    title = title,
    contentNote = contentNote
)

fun NoteDbModel.toNote() = Note(
    id = id,
    title = title,
    contentNote = contentNote
)

fun Note.toNoteDbModel() = NoteDbModel(
    id = id,
    title = title,
    contentNote = contentNote
)
package com.vasyancoder.remindme.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDbModel(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "contentNote") val contentNote: String
)
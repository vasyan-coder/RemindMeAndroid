package com.vasyancoder.remindme.data.repository

import android.content.Context
import com.vasyancoder.remindme.data.mapper.toNote
import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.data.network.retrofit.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val context: Context
) {

    private val apiService = ApiFactory.apiService
    private val dispatcherIO = Dispatchers.IO

    fun getNotesList(): Flow<List<Note>> = flow {
        while (true) {
            val notes = apiService.getNotesList().map { it.toNote() }
            emit(notes)
            delay(REFRESH_TIME)
        }
    }

    fun addNote(note: Note): Flow<Boolean> = flow {
        val isAdded = apiService.addNote(
            title = note.title,
            body = note.contentNote,
        )
        emit(isAdded)
    }

    fun saveInCache(note: Note) {
        TODO()
    }

    fun getNotesFromCache(): Flow<List<Note>> {
        TODO()
    }

    companion object {

        private const val REFRESH_TIME = 2000L
    }
}
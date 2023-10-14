package com.vasyancoder.remindme.data.repository

import android.content.Context
import com.vasyancoder.remindme.data.database.NoteRoomDatabase
import com.vasyancoder.remindme.data.mapper.toNote
import com.vasyancoder.remindme.data.mapper.toNoteDbModel
import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.data.network.retrofit.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NoteRepositoryImpl(
    private val context: Context
) {

    private val apiService = ApiFactory.apiService
    private val dispatcherIO = Dispatchers.IO
    private val noteDao by lazy {
        val db: NoteRoomDatabase = NoteRoomDatabase.getDatabase(context)
        db.noteDao()
    }

    fun getNotesList(): Flow<List<Note>> = flow {
        while (true) {
            try {
                val notes = apiService.getNotesList().map { it.toNote() }
                emit(notes)
            } catch (_: Exception) {
            }
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

    fun notesSaveInCache(notes: List<Note>) {
        CoroutineScope(dispatcherIO).launch {
            noteDao.insertAllNotes(notes.map { it.toNoteDbModel() })
        }
    }

    fun getNotesFromCache(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { notes -> notes.map { it.toNote() } }
    }

    companion object {

        private const val REFRESH_TIME = 2000L
    }
}
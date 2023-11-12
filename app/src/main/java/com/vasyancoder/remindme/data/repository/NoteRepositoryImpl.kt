package com.vasyancoder.remindme.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.vasyancoder.remindme.data.database.dao.NoteDao
import com.vasyancoder.remindme.data.mapper.toNote
import com.vasyancoder.remindme.data.mapper.toNoteDbModel
import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.data.network.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val noteDao: NoteDao
) {

    private val dispatcherIO = Dispatchers.IO

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

    fun saveImage(bitmap: Bitmap) {
        val imagesFolder = File(context.cacheDir, "images")
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "test")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.flush()
            stream.close()
        } catch (_: IOException) {
        }
    }

    companion object {

        private const val REFRESH_TIME = 2000L
    }
}
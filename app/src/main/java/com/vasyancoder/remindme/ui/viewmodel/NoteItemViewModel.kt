package com.vasyancoder.remindme.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.data.repository.NoteRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteItemViewModel @Inject constructor(
    private val repository: NoteRepositoryImpl
) : ViewModel() {

    val notesList = repository.getNotesList().onEach {
        repository.notesSaveInCache(it)
    }

    val notesListFromCache = repository.getNotesFromCache()

    private val _noteIsAdded = MutableStateFlow<Boolean?>(null)
    val noteIsAdded = _noteIsAdded.asStateFlow()

    fun addNote(title: String, contentNote: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(
                Note(
                    title = title,
                    contentNote = contentNote
                )
            ).collect {
                _noteIsAdded.value = it
            }
        }
    }
}
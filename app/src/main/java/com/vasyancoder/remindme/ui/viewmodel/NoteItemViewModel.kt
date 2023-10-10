package com.vasyancoder.remindme.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vasyancoder.remindme.data.model.Note
import com.vasyancoder.remindme.data.repository.NoteRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NoteRepositoryImpl(application)

    val notesList = repository.getNotesList()

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
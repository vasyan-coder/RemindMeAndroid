package com.vasyancoder.remindme.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.vasyancoder.remindme.data.repository.NoteRepositoryImpl
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val repository: NoteRepositoryImpl
) : ViewModel() {

    fun saveImage(bitmap: Bitmap) {
        repository.saveImage(bitmap)
    }
}
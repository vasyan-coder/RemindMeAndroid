package com.vasyancoder.remindme

import android.app.Application
import com.vasyancoder.remindme.di.DaggerApplicationComponent

class NoteApp: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}
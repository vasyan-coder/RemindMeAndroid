package com.vasyancoder.remindme.di

import androidx.lifecycle.ViewModel
import com.vasyancoder.remindme.ui.viewmodel.NoteItemViewModel
import com.vasyancoder.remindme.ui.viewmodel.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NoteItemViewModel::class)
    @Binds
    fun bindMainViewModel(impl: NoteItemViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(impl: ProfileViewModel): ViewModel
}
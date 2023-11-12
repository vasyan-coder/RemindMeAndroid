package com.vasyancoder.remindme.di

import android.content.Context
import com.vasyancoder.remindme.ui.MainActivity
import com.vasyancoder.remindme.ui.fragment.MainFragment
import com.vasyancoder.remindme.ui.fragment.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

}
package com.vasyancoder.remindme.di

import android.content.Context
import com.vasyancoder.remindme.data.database.NoteRoomDatabase
import com.vasyancoder.remindme.data.database.dao.NoteDao
import com.vasyancoder.remindme.data.network.retrofit.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun getApiService(): ApiService {
        val BASE_URL = "http://10.0.2.2:8080/"

        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(
            ApiService::class.java
        )
    }

    @Provides
    @Singleton
    fun getNoteDao(context: Context): NoteDao {
        val db: NoteRoomDatabase = NoteRoomDatabase.getDatabase(context)
        return db.noteDao()
    }
}
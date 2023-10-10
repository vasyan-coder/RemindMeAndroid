package com.vasyancoder.remindme.data.network.retrofit

import com.vasyancoder.remindme.data.network.dto.NoteDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    @GET("notesList")
    suspend fun getNotesList(): List<NoteDto>

    @POST("notesList")
    suspend fun addNote(
        @Query("title") title: String,
        @Query("body") body: String,
    ): Boolean

    @PUT("notesList")
    suspend fun deleteNotes(
        @Query("ids") ids: List<Long>
    ): Boolean
}
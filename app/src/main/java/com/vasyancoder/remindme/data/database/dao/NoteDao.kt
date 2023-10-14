package com.vasyancoder.remindme.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasyancoder.remindme.data.database.model.NoteDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM NoteDbModel")
    fun getAllNotes(): Flow<List<NoteDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNotes(notes: List<NoteDbModel>)
}
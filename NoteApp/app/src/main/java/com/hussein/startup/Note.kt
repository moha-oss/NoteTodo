package com.hussein.startup

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity
data class Note(
        @PrimaryKey(autoGenerate = true) val ID: Int,
        @ColumnInfo(name = "Title") val Title: String,
        @ColumnInfo(name = "Description") val Description: String
        )

//TODO: 2- Define Doa

@Dao
interface NoteDao{
    @Insert
    fun insert (vararg note:Note)
    @Delete
    fun delete (vararg note:Note)
    @Update
    fun update (vararg note:Note)
    @Query("select * from Note where Title like :title")
    fun load(title:String):List<Note>
}

// TODO: 3- create database
@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDB:RoomDatabase(){
    abstract fun NoteDao():NoteDao
}


//TODO: 4- Create database instance
class DBmanager{
    @Volatile
    private var instance:NoteDB?=null
    fun getDatabase(context:Context):NoteDB?{
        synchronized(NoteDB::class.java){
            if(instance==null){
                instance= Room.databaseBuilder(context.applicationContext,
                NoteDB::class.java!!,"MynotDB").allowMainThreadQueries().build()
            }
        }
        return instance
    }
}


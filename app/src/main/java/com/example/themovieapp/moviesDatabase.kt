package com.example.themovieapp
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Dao


@Database(entities = arrayOf(movies::class), version = 1)
abstract class moviesDatabase : RoomDatabase() {
    //this is the database class
    //Here we define the version of the database and the entity and DAO that i am using
    abstract fun moviesDao():moviesDao
    companion object {
        private var INSTANCE: moviesDatabase? = null
        fun getInstance(context: Context): moviesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    moviesDatabase::class.java,
                    "data")
                    .build()
            }
            return INSTANCE as moviesDatabase
        }
    }
}
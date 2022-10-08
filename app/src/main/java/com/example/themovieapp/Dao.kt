package com.example.themovieapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface moviesDao{
    //this is the DAO which contain all SQL query to extract or or manipulate the data in database
    @Query("SELECT * FROM movies_tables")
    fun allStudents():List<movies>
    @Query("SELECT * FROM movies_tables WHERE Actors LIKE '%' || :word || '%'")
    fun findActor(word:String):List<movies>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: movies)
}


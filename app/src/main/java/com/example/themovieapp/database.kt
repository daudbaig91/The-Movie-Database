package com.example.themovieapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.security.AccessControlContext
val DATABASE_NAME = "MYDATA"
val TABLE_NAME = "MOVIES"
val Title = "Title"
val Year = "Year"
val Rated = "Rated"
val Released = "Released"
val Runtime = "Runtime"
val Genre = "Genre"
val Director = "Director"
val Writer = "Writer"
val Actors = "Actors"
val Plot = "Plot"
// in thsi class i define the entities which will be used to represent the tables in the database
class database(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val create = "CREATE TABLE " + TABLE_NAME+" ("+ Title+ " VARCHAR(256),"+
                    Year +" VARCHAR(256),"+
                    Rated+" INTEGER,"+
                Released+" VARCHAR(256),"+
                Runtime+" VARCHAR(256),"+
                Genre+" VARCHAR(256),"+
                Director+" VARCHAR(256),"+
                Writer+" VARCHAR(256),"+
                Actors+" VARCHAR(256),"+
                Plot + " TEXT)"
            p0?.execSQL(create)
             }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
        }


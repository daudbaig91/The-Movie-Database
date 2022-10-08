package com.example.themovieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import org.json.JSONObject
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mDb:moviesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //buttons for main page for options
        val b1 = findViewById<Button>(R.id.b1)
        val b2 = findViewById<Button>(R.id.b2)
        val b3 = findViewById<Button>(R.id.b3)
        val b = findViewById<Button>(R.id.b)

        mDb = moviesDatabase.getInstance(applicationContext)
        b1.setOnClickListener() {
            //function which converts file to jason and saves to Room databse
            readFile("text2.json")
            Toast.makeText(this,"Data Inserted", Toast.LENGTH_SHORT).show()
        }
        b2.setOnClickListener() {
            //open new activity
            val intent = Intent(this, findmovie::class.java)
            startActivity(intent)
        }
        b3.setOnClickListener() {
            //open new activity
            val intent = Intent(this, actorSearch::class.java)
            startActivity(intent)
        }
        b.setOnClickListener() {
            //open new activity
            val intent = Intent(this, actorsearchweb::class.java)
            startActivity(intent)
        }
    }
    fun readFile(fileName: String){
        //convert sting input to jsonobject
        val inputString = JSONObject((application.assets.open(fileName).bufferedReader().use { it.readText() }))
        //converting  json to array
        val items = inputString.getJSONArray("Search")

        Thread {//doing in a new thread as it was giving errors
        for (i in 0 until items.length()) {
            //looping through each movie and adding to the database
            val item = items.getJSONObject(i)
            var thismovie = movies(item.getString("Title"), item.getString("Year").toInt(),
                item.getString("Rated"), item.getString("Released"),
                item.getString("Runtime"), item.getString("Genre"),
                item.getString("Director"),item.getString("Writer"),
                item.getString("Actors"), item.getString("Plot"))
            //passing each movie object to modiesDao class to write to Room
            //passsing the movie object containing all the information and inserting  to the database
            mDb.moviesDao().insert(thismovie)
        }
        }.start()
        }

    }

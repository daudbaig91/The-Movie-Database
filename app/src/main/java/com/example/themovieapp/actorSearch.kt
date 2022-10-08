package com.example.themovieapp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync
class actorSearch : AppCompatActivity() {
    private lateinit var mDb:moviesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor_search)
        mDb = moviesDatabase.getInstance(applicationContext)
        val actbttn = findViewById<Button>(R.id.searchactbttn)
        actbttn.setOnClickListener(){
            doAsync {
                val textact = findViewById<EditText>(R.id.textactor).text.toString()
                val list = mDb.moviesDao().findActor(textact)
                // calling DAO with the actor the user wants to search which will produce a list of
                //movies containing the actors name
                activityUiThread {
                    val scroll = findViewById<ScrollView>(R.id.scrollv)
                    scroll.scrollTo(0,0)
                    //whenever the user search a new actors, the Textview gets cleared and scroll
                    //postion is reseted, else if a long list is produced and the user scrolls all
                    //the way down, and he searches a again the list might not even show as the
                    //scrool position is uch below
                    val printthis = findViewById<TextView>(R.id.actordisplay)
                    printthis.text = ""
                    for (selected in list){
                        //displaying the title of each movie from the list
                        printthis.append ("Movie Tile: ${selected.Title}\n\n\n")
                         }
                     }
                }
            }
        }
    }

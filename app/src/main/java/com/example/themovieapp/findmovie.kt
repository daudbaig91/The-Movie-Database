package com.example.themovieapp

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException


class findmovie : AppCompatActivity() {
    val client = OkHttpClient()
    private lateinit var mDb:moviesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findmovie)

         mDb = moviesDatabase.getInstance(applicationContext)

        val button = findViewById<Button>(R.id.searchbttn)
        val button1 = findViewById<Button>(R.id.button2)

        button.setOnClickListener {
            if (mutableList.size > 0){
            mutableList.removeAt(0)}
            //variable text contains name of the movie to be searched
            var text =(findViewById<EditText>(R.id.enter)).text.toString()
            //running the call function which retrieves the data from the web service
            val key :String = BuildConfig.API_KEY
            call("https://www.omdbapi.com/?t=$text&type=movie&apikey=$key")
            val printthis = findViewById<TextView>(R.id.display)
            val selected : movies = mutableList.get(0)
            //when the movie is retrived, the information gets diplsayed in the texview
            printthis.text = ("Tile: ${selected.Title}\nYear: ${selected.Year}\nRated: ${selected.Rated}\n" +
                    "Released: ${selected.Released}\nRuntime: ${selected.Runtime}\nGenre: ${selected.Genre}\n" +
                    "Director: ${selected.Director}\nWriter: ${selected.Writer}\nActors: ${selected.Actors}\n" +
                    "Plot: ${selected.Plot}")
        }

        button1.setOnClickListener {
            if (mutableList.size == 0){
                Toast.makeText(this,"Please search the movie that u want to add", Toast.LENGTH_SHORT).show()
            }
            else{
                Thread {
                    //if user wants to save the movie, the movie gets inserted in the room database
                    mDb.moviesDao().insert(mutableList[0])
                }.start()
                Toast.makeText(this,"Data Inserted", Toast.LENGTH_SHORT).show()
                //var db = database(this)//db.insertdata(mutableList.get(0))//SQLITE version
            }
        }
    }
    //global list contains the movie searched
    val mutableList = mutableListOf<movies>()
    fun call(url: String) {
        //we make a request providing the url
        var request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response){
                try {
                //if there is a response, the api responds with the json of the movie
                val jsonObject = JSONTokener(response.body()?.string().toString()).nextValue() as JSONObject
                //we convert the json files passed from the api to a jason object and it to the list
                mutableList.add( movies(jsonObject.getString("Title"), jsonObject.getString("Year").toInt(),
                        jsonObject.getString("Rated"), jsonObject.getString("Released"),
                        jsonObject.getString("Runtime"), jsonObject.getString("Genre"),
                        jsonObject.getString("Director"),jsonObject.getString("Writer"),
                        jsonObject.getString("Actors"), jsonObject.getString("Plot")))
                } catch (e : JSONException) {
                    toast("Movie not found")
                }
             }
            })
            Thread.sleep(1000L)
        }
}
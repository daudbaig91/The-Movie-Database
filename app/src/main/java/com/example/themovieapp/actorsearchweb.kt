package com.example.themovieapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class actorsearchweb : AppCompatActivity() {
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actorsearchweb)
        val actbttn = findViewById<Button>(R.id.searchactbttn1)
        actbttn.setOnClickListener() {
            val textact = findViewById<EditText>(R.id.textactor1).text.toString()
            mutableList = mutableListOf<String>()
            call("https://www.omdbapi.com/?s=*$textact*&type=movie&apikey=3339fd8a")
            //using call argument passing the query and movie to find all matching values
            //re used the call functiuon as main
            val printthis = findViewById<TextView>(R.id.actordisplay1)
            printthis.text =""
            //max 10 retrievals objects aloud by api
            for( st in mutableList){
                //printing all movies in list
                printthis.append(st.toString()+"\n\n")
            }
        }
    }
    var mutableList = mutableListOf<String>()
    fun call(url: String) {

        var request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                toast("Error retrieving movies")
            }
            override fun onResponse(call: Call, response: Response){
                try {
                val jsonObject = JSONTokener(response.body()?.string().toString()).nextValue() as JSONObject
                    //convert sting input to jsonobject

                val items = jsonObject.getJSONArray("Search")
                for (i in 0 until items.length()) {
                    //looping through each movie and adding to the database
                    val item = items.getJSONObject(i)
                    //we add each title for each movie from json file retrireved from api
                    mutableList.add(item.getString("Title"))
                }
                } catch (e : JSONException) {
                    mutableList.add("No results with such substring")
                }
            }
        })
        Thread.sleep(1000L)//pausing the thread for 1sec so all data can be loaded
    }
}
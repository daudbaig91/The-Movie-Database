package com.example.themovieapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_tables")
class movies {//movie class which saves all the info for each movie
    //class saving all movie details
    @PrimaryKey
    var id:Long? = null
    var Title: String = ""
    var Year: Int = 0
    var Rated: String = ""
    var Released: String = ""
    var Runtime: String = ""
    var Genre: String = ""
    var Director: String = ""
    var Writer: String = ""
    var Actors: String = ""
    var Plot: String = ""

    constructor(Title: String,Year: Int,Rated: String,Released: String,
               Runtime: String,Genre: String,Director: String,Writer: String
                ,Actors: String,Plot: String){
        this.Title =Title
        this.Year=Year
        this.Rated=Rated
        this.Released=Released
        this.Runtime=Runtime
        this.Genre=Genre
        this.Director=Director
        this.Writer=Writer
        this.Actors=Actors
        this.Plot=Plot
    }

}
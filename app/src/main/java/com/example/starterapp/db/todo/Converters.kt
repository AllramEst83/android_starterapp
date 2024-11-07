package com.example.starterapp.db.todo

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDate(date: Date) : Long{
        return  date.time
    }
    @TypeConverter
    fun toDate(time: Long) : Date{
        return Date(time)
    }
    @TypeConverter
    fun fromBoolean(done: Boolean) : Int{
        return if (done) 1 else 0
    }
    @TypeConverter
    fun toBoolean(done: Int) : Boolean{
        return done == 1
    }
}
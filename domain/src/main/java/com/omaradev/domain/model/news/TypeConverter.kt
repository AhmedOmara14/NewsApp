package com.omaradev.domain.model.news

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeConverter {
    val gson : Gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?) : Article? {
        if(data == null)return null
        val listType: Type = object :TypeToken<Article?>() {}.type
        return gson.fromJson<Article?>(data, listType)
    }

    @TypeConverter
    fun jsonToSource(json: String): Source = gson.fromJson(json, Source::class.java)


    @TypeConverter
    fun someObjectListToString(someobject: Article?): String?
    {
        return gson.toJson(someobject)
    }
}

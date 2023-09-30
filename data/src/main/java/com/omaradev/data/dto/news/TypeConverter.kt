package com.omaradev.data.dto.news

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeConverter {
    val gson : Gson = Gson()
    @TypeConverter
    fun stringToSomeObjectList(data: String?) : ArticleNetwork? {
        if(data == null)return null
        val listType: Type = object :TypeToken<ArticleNetwork?>() {}.type
        return gson.fromJson<ArticleNetwork?>(data, listType)
    }

    @TypeConverter
    fun jsonToSource(json: String): SourceNetwork = gson.fromJson(json, SourceNetwork::class.java)


    @TypeConverter
    fun someObjectListToString(someobject: ArticleNetwork?): String?
    {
        return gson.toJson(someobject)
    }
}

package com.example.todoapp.data.model.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class TodoApp(
    val title: String,
    val body: String,
    val checkBox: Boolean
)

class TodoTypeConverter() {
    private val gson = Gson()

    @TypeConverter
    fun todoAppToGson(todo: List<TodoApp>): String {
        return gson.toJson(todo)
    }

    @TypeConverter
    fun gsonToDo(todo: String): List<TodoApp> {
        val objectType = object : TypeToken<List<TodoApp>>() {}.type
        return gson.fromJson(todo, objectType)
    }
}
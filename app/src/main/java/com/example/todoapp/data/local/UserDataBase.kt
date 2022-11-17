package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todoapp.data.model.entities.LoggedInUser
import com.example.todoapp.data.model.entities.TodoList
import com.example.todoapp.data.model.entities.TodoTypeConverter

@Database(
    entities = [LoggedInUser::class, TodoList::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TodoTypeConverter::class)
abstract class UserDataBase : RoomDatabase() {
    abstract fun getDao(): DAO

    companion object {
        const val USER_TABLE_NAME = "UserTable.db"
    }
}
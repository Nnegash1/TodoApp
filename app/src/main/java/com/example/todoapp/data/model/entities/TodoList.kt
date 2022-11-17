package com.example.todoapp.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = LoggedInUser::class,
        parentColumns = arrayOf("pk"),
        childColumns = arrayOf("user"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TodoList(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int = 0,
    val todoList: List<TodoApp>,
    @ColumnInfo(index = true)
    val user: Int
)


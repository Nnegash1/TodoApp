package com.example.todoapp.data.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.todoapp.data.model.entities.LoggedInUser
import com.example.todoapp.data.model.entities.TodoList

data class UserTodo(
    @Embedded val user: LoggedInUser,
    @Relation(
        parentColumn = "pk",
        entityColumn = "todoId"
    )
    val todo: TodoList
)
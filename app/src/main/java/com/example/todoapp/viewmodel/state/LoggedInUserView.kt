package com.example.todoapp.viewmodel.state

import com.example.todoapp.data.model.entities.TodoApp

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val pk: Int,
    val todoList: List<TodoApp>,
    val isLoggedIn: Boolean
    //... other data fields that may be accessible to the UI
)
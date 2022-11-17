package com.example.todoapp.viewmodel.state

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val pk : Int
    //... other data fields that may be accessible to the UI
)
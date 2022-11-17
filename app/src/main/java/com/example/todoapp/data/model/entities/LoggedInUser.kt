package com.example.todoapp.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity
data class LoggedInUser(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val userId: String,
    val displayName: String,
    val email: String?,
    val password: String?
)
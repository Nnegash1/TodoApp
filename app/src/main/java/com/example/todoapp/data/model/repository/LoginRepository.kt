package com.example.todoapp.data.model.repository

import com.example.todoapp.data.LoginDataSource
import com.example.todoapp.data.Result
import com.example.todoapp.data.model.entities.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    suspend fun logout(pk: Int, isLoggedIn: Boolean) {
        user = null
        dataSource.logout(pk, isLoggedIn)
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(email = username, password = password)
        if (result is Result.Success) {
            if (result.data.isLoggedIn) {
                setLoggedInUser(result.data)
            }
        }
        return result
    }

    suspend fun registration(
        username: String,
        email: String,
        password: String
    ): Result<LoggedInUser> {
        // handle login
        val result = dataSource.signUp(username, email, password)
        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }
        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
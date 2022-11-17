package com.example.todoapp.data

import com.example.todoapp.data.local.DAO
import com.example.todoapp.data.model.entities.LoggedInUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource @Inject constructor(private val dao: DAO) {

    suspend fun login(email: String, password: String): Result<LoggedInUser> {
        val scope = CoroutineScope(Dispatchers.Default)
        val result = scope.async {
            try {
                // handle loggedInUser authentication
                val result = dao.findUser(email, password)
                return@async Result.Success(result)
            } catch (e: Throwable) {
                return@async Result.Error(IOException("Error logging in", e))
            }
        }

        return result.await()
    }

    suspend fun signUp(username: String, email: String, password: String): Result<LoggedInUser> {
        val scope = CoroutineScope(Dispatchers.Default)
        val result = scope.async {
            try {
                // handle loggedInUser authentication
                val fakeUser = LoggedInUser(
                    userId = UUID.randomUUID().toString(),
                    displayName = username,
                    email = email,
                    password = password
                )
                dao.insertUser(fakeUser)
                return@async Result.Success(fakeUser)
            } catch (e: Throwable) {
                return@async Result.Error(IOException("Error logging in", e))
            }
        }

        return result.await()
    }

    fun logout() {
        // revoke authentication
    }
}
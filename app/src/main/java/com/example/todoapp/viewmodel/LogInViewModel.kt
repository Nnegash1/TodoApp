package com.example.todoapp.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.data.Result
import com.example.todoapp.data.model.repository.LoginRepository
import com.example.todoapp.data.model.repository.TodoRepository
import com.example.todoapp.viewmodel.state.LoggedInUserView
import com.example.todoapp.viewmodel.state.LoginFormState
import com.example.todoapp.viewmodel.state.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val todo: TodoRepository
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) = viewModelScope.launch {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(email, password)
        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(
                    success = LoggedInUserView(
                        displayName = result.data.displayName,
                        pk = result.data.pk,
                        todoList = todo.getUserTodo(result.data.pk)
                    )
                )
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun signUpNewAccount(username: String, email: String, password: String) =
        viewModelScope.launch {
            loginRepository.registration(username, email, password)
        }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
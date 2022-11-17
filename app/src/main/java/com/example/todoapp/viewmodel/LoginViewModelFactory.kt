package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.model.repository.LoginRepository
import com.example.todoapp.data.model.repository.TodoRepository
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory @Inject constructor(
    private val repo: LoginRepository,
    private val todo: TodoRepository
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = repo
            ) as T
        } else if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(
                repo = todo
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
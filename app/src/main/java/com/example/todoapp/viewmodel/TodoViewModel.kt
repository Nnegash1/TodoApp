package com.example.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.data.model.repository.LoginRepository
import com.example.todoapp.data.model.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repo: TodoRepository, private val login: LoginRepository) :
    ViewModel() {

    companion object {
        var localTodoList: MutableList<TodoApp> = mutableListOf()
    }

    private val _todoList: MutableLiveData<MutableList<TodoApp>> = MutableLiveData()
    val todoList get() = _todoList

    fun addToList(todo: TodoApp) = viewModelScope.launch {
        localTodoList.add(todo)
        _todoList.value = localTodoList
    }

    fun saveDataToDataBase(pk: Int, todo: List<TodoApp>) = viewModelScope.launch {
        repo.updateTodo(pk, todo)
    }

    fun update() {
        _todoList.value = localTodoList
    }

    fun logout() {
        login.logout()
    }

    fun initData(pk: Int) = viewModelScope.launch {
        localTodoList.addAll(repo.getUserTodo(pk).toMutableList())
        _todoList.value = localTodoList
    }
}
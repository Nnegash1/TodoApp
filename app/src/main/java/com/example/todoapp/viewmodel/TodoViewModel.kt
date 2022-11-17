package com.example.todoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.data.model.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(val repo: TodoRepository) : ViewModel() {

    private val _todoList: MutableLiveData<MutableList<TodoApp>> = MutableLiveData(mutableListOf())
    val todoList get() = _todoList

    fun addTodo(pk: Int, todo: List<TodoApp>) = viewModelScope.launch {
        repo.updateTodo(pk, todo)
    }

    fun getToDo(pk: Int) = viewModelScope.launch {
        val result = repo.getUserTodo(pk)
        _todoList.value = result.toMutableList()
    }

    fun addToList(todo: TodoApp) {
        _todoList.value?.add(todo)
    }

}
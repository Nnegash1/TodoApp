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


    var localTodoList: MutableList<TodoApp> = mutableListOf()
    private val _todoList: MutableLiveData<MutableList<TodoApp>> = MutableLiveData()
    private val _todo: MutableLiveData<TodoApp> = MutableLiveData()
    val todoList get() = _todoList
    val todo get() = _todo

    fun addToList(todo: TodoApp) = viewModelScope.launch {
        localTodoList.add(todo)
        _todoList.value = localTodoList
    }

    fun saveDataToDataBase(pk: Int, todo: List<TodoApp>) = viewModelScope.launch {
        repo.updateTodo(pk, todo)
    }

    fun getTodoAtIndex(index: Int) = viewModelScope.launch {
        try {
            localTodoList.removeAt(index)
        } catch (e: IndexOutOfBoundsException) {
            Log.d("TAG", "Index out of bound: ")
        }
    }

    fun setTodoAtIndex(index: Int) = viewModelScope.launch {
        try {
            todo.value?.let {
                localTodoList[index] = it
            }
        } catch (e: IndexOutOfBoundsException) {
            Log.d("TAG", "Index out of bound: ")
        }
    }

    fun update() {
        _todoList.value = localTodoList
    }

    fun logout(pk: Int) = viewModelScope.launch {
        login.logout(pk)
    }

    fun initData(pk: Int) = viewModelScope.launch {
        localTodoList.addAll(repo.getUserTodo(pk).toMutableList())
        _todoList.value = localTodoList
    }
}
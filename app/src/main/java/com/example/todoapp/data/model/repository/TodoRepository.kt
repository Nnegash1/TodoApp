package com.example.todoapp.data.model.repository

import android.util.Log
import com.example.todoapp.data.local.DAO
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.data.model.entities.TodoList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class TodoRepository(private val dao: DAO) {
    suspend fun updateTodo(pk: Int, todo: List<TodoApp>) {
        withContext(Dispatchers.Default) {
            val userExist = dao.updateTodo(pk = pk, todo = todo)
            if (userExist == 0) {
                dao.insertTodo(TodoList(todoList = todo, user = pk))
            }
        }
    }

    suspend fun getUserTodo(pk: Int): List<TodoApp> {
        val scope = CoroutineScope(Dispatchers.Default)
        val result = scope.async {
            try {
                val todo = dao.getTodo(pk)
                return@async todo.todoList
            } catch (e: NullPointerException) {
                Log.d("TAG", "getUserTodo: $e")
                return@async listOf(TodoApp("Failed", "Body", false))
            }

        }
        return result.await()
    }
}
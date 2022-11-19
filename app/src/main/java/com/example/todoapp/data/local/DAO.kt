package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.Query
import androidx.room.Transaction
import com.example.todoapp.data.model.entities.LoggedInUser
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.data.model.entities.TodoList
import com.example.todoapp.data.model.relation.UserTodo

@Dao
interface DAO {
    @Query("select * from LoggedInUser where email =:user and password =:password LIMIT 1")
    suspend fun findUser(user: String, password: String): LoggedInUser

    @Transaction
    @Query("select * from LoggedInUser")
    suspend fun filterTodo(): List<UserTodo>

    @Insert
    suspend fun insertTodo(todo: TodoList)

    @Query("Update TodoList Set  todoList=:todo Where user=:pk")
    suspend fun updateTodo(todo: List<TodoApp>, pk: Int): Int

    @Query("select * from TodoList where user=:pk")
    suspend fun getTodo(pk: Int): TodoList

    @Insert(onConflict = ABORT)
    suspend fun insertUser(user: LoggedInUser)
}
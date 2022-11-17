package com.example.todoapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.databinding.TodoCardBinding


class TodoAdapter : RecyclerView.Adapter<CardHolder>() {
    private val todoList: MutableList<TodoApp> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(
            TodoCardBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.display(todoList[position])
    }

    fun update(newList : List<TodoApp>){
        val oldSize = todoList.size
        todoList.clear()
        notifyItemRangeChanged(0 , oldSize)
        todoList.addAll(newList)
        notifyItemRangeChanged(0 , newList.size)
    }
}

class CardHolder(private val binding: TodoCardBinding) : ViewHolder(binding.root) {
    fun display(card: TodoApp) {
        with(binding) {
            title.text = card.title
            checkbox.isChecked = false
        }
    }
}
package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.databinding.FragmentEditToDoScreenBinding
import com.example.todoapp.viewmodel.LoginViewModelFactory
import com.example.todoapp.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditToDoScreen : Fragment() {


    @Inject
    lateinit var factory: LoginViewModelFactory
    private val args by navArgs<EditToDoScreenArgs>()
    private lateinit var binding: FragmentEditToDoScreenBinding
    private lateinit var todo: TodoApp
    private val todoVm: TodoViewModel by activityViewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentEditToDoScreenBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoVm.todo.observe(viewLifecycleOwner) { setInput(it) }

        binding.addTodo.setOnClickListener { getInput() }

        binding.deleteBtn.setOnClickListener {
            todoVm.getTodoAtIndex(args.index)
            findNavController().popBackStack()
        }

        todo = todoVm.localTodoList[args.index]

        setInput(todo)
    }

    private fun getInput() {
        var title: String
        var body: String
        var checked: Boolean

        with(binding) {
            title = todoTitle.text.toString()
            body = todoDetail.text.toString()
            checked = complete.isChecked
        }

        if (!(title.isBlank() || body.isBlank())) {
            todoVm.todo.value = TodoApp(title, body, checked)
            todoVm.setTodoAtIndex(args.index)
            findNavController().popBackStack()
        } else {
            Toast.makeText(
                requireContext(),
                "Please fill out all the fields !",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setInput(todo: TodoApp) {
        with(binding) {
            todoTitle.setText(todo.title)
            todoDetail.setText(todo.body)
            complete.isChecked = todo.checkBox
        }
    }
}
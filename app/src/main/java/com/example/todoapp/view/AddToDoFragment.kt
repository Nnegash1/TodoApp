package com.example.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.databinding.FragmentAddToDoBinding
import com.example.todoapp.viewmodel.LoginViewModelFactory
import com.example.todoapp.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddToDoFragment : Fragment() {

    @Inject
    lateinit var factory: LoginViewModelFactory
    lateinit var binding: FragmentAddToDoBinding
    private val addVm: TodoViewModel by activityViewModels { factory }
    private var title: String = ""
    private var body: String = ""
    private var checked: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddToDoBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTodo.setOnClickListener {
            binding.let {
                title = it.todoTitle.text.toString()
                body = it.todoDetail.text.toString()
                checked = it.complete.isChecked
            }

            if (title.isBlank() || body.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill out all the fields !",
                    Toast.LENGTH_SHORT
                ).show()
            }
            addVm.addToList(TodoApp(title, body, checked))
            findNavController().popBackStack()
        }
    }
}
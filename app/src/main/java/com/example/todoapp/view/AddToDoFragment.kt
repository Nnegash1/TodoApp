package com.example.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddToDoBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTodo.setOnClickListener {
            addVm.addToList(TodoApp("Nahom", "Negash", false))
            findNavController().popBackStack()
        }
    }
}
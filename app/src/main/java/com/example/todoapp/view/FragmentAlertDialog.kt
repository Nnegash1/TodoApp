package com.example.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.databinding.FragmentAlertDialogBinding
import com.example.todoapp.viewmodel.LoginViewModelFactory
import com.example.todoapp.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FragmentAlertDialog(val pk: Int?, val list: List<TodoApp>) : DialogFragment() {

    @Inject
    lateinit var factory: LoginViewModelFactory
    private val todo: TodoViewModel by activityViewModels { factory }
    lateinit var binding: FragmentAlertDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAlertDialogBinding.inflate(layoutInflater).also { binding = it }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.logout.setOnClickListener {
            if (pk != null) {
                todo.saveDataToDataBase(pk, list)
            }
        }
    }
}
package com.example.todoapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.model.entities.TodoApp
import com.example.todoapp.databinding.FragmentTodoBinding
import com.example.todoapp.view.adapter.TodoAdapter
import com.example.todoapp.viewmodel.LoginViewModelFactory
import com.example.todoapp.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoFragment : Fragment() {

    @Inject
    lateinit var factory: LoginViewModelFactory
    private lateinit var bindings: FragmentTodoBinding
    private val todoVm: TodoViewModel by activityViewModels { factory }
    private val args by navArgs<TodoFragmentArgs>()
    private val adapter = TodoAdapter()
    private var _todoList: List<TodoApp> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentTodoBinding.inflate(layoutInflater).also { bindings = it }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoVm.initData(args.pk)
        todoVm.update()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoVm.todoList.observe(viewLifecycleOwner) {
            _todoList = it.toList()
            Log.d("TAG", "Testing: $_todoList")
            adapter.update(it)
        }

        bindings.profile.setOnClickListener {
            val dialog = FragmentAlertDialog(args.pk, _todoList)
            dialog.show(parentFragmentManager, "Show")
        }

        bindings.addTodo.setOnClickListener {
            val action = TodoFragmentDirections.actionTodoFragmentToAddToDo()
            findNavController().navigate(action)
        }

        bindings.user.text = args.user
        initAdapter()
    }

    private fun initAdapter() {
        bindings.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bindings.recyclerView.adapter = adapter
    }

}
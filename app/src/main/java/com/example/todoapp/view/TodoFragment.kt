package com.example.todoapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.FragmentTodoBinding
import com.example.todoapp.view.adapter.TodoAdapter
import com.example.todoapp.viewmodel.LoginViewModel
import com.example.todoapp.viewmodel.LoginViewModelFactory
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.viewmodel.state.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TodoFragment : Fragment() {

    @Inject
    lateinit var factory: LoginViewModelFactory
    private lateinit var bindings: FragmentTodoBinding
    private val todoVm: TodoViewModel by activityViewModels { factory }
    private val loginViewModel: LoginViewModel by activityViewModels { factory }
    private val adapter = TodoAdapter()
    var pk: Int = 2
    var userName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTodoBinding.inflate(layoutInflater).also { bindings = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginResult.observe(viewLifecycleOwner) { it: LoginResult ->
                if (it.error == null) {
                    it.success?.let {
                        pk = it.pk
                        userName = it.displayName
                    }
                    bindings.user.text = userName
                }
            }

            todoVm.todoList.observe(viewLifecycleOwner) {
                todoVm.addTodo(pk, it)
                adapter.update(it)
            }
        }

        bindings.addTodo.setOnClickListener {
            val action = TodoFragmentDirections.actionTodoFragmentToAddToDo()
            findNavController().navigate(action)
        }
        Log.d("TAG", "onViewCreated: $pk")
        todoVm.getToDo(2)
        initAdapter()
    }

    private fun initAdapter() {
        bindings.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bindings.recyclerView.adapter = adapter
    }

}
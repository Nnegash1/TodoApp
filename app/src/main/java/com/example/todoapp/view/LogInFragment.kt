package com.example.todoapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.databinding.FragmentLogInBinding
import com.example.todoapp.viewmodel.LoginViewModel
import com.example.todoapp.viewmodel.LoginViewModelFactory
import com.example.todoapp.viewmodel.state.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogInFragment : Fragment() {


    @Inject
    lateinit var factory: LoginViewModelFactory
    lateinit var binding: FragmentLogInBinding
    var email: String = ""
    var password: String = ""
    private val vm: LoginViewModel by activityViewModels() { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLogInBinding.inflate(layoutInflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            vm.loginResult.observe(viewLifecycleOwner) { it: LoginResult ->
                if (it.error == null) {
                    val action = LogInFragmentDirections.actionLogInFragmentToTodoFragment()
                    findNavController().navigate(action)
                }
            }
        }

        binding.newAccount.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToRegisterationFragment()
            findNavController().navigate(action)
        }
        binding.signIn.setOnClickListener {
            email = binding.username.text.toString()
            password = binding.password.text.toString()
            vm.login(email, password)
        }
    }
}
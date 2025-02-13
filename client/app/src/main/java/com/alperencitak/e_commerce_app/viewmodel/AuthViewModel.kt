package com.alperencitak.e_commerce_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperencitak.e_commerce_app.model.LoginRequest
import com.alperencitak.e_commerce_app.model.RegisterRequest
import com.alperencitak.e_commerce_app.model.User
import com.alperencitak.e_commerce_app.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _loginResponse = MutableStateFlow<User?>(null)
    val loginResponse: StateFlow<User?> get() = _loginResponse

    private val _registerResponse = MutableStateFlow<User?>(null)
    val registerResponse: StateFlow<User?> get() = _registerResponse

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            try {
                _loading.value = true
                _loginResponse.value = authRepository.login(loginRequest)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun register(registerRequest: RegisterRequest){
        viewModelScope.launch {
            try {
                _loading.value = true
                _registerResponse.value = authRepository.register(registerRequest)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

}
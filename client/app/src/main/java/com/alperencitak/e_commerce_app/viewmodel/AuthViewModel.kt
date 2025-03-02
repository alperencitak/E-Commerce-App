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
    val loginResponse: StateFlow<User?> = _loginResponse

    private val _registerResponse = MutableStateFlow<User?>(null)
    val registerResponse: StateFlow<User?> = _registerResponse

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            try {
                _loading.value = true
                val user = authRepository.login(loginRequest)
                _loginResponse.value = user
                _currentUserId.value = user.user_id.toString()
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

    fun logout(){
        viewModelScope.launch {
            try {
                _loading.value = true
                authRepository.logout()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun getCurrentUserId(){
        viewModelScope.launch {
            try {
                _loading.value = true
                _currentUserId.value = authRepository.getCurrentUserId()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

}
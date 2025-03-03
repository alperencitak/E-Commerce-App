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
import com.alperencitak.e_commerce_app.handler.Result

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _loginResponse = MutableStateFlow<Result<User>?>(null)
    val loginResponse: StateFlow<Result<User>?> = _loginResponse

    private val _registerResponse = MutableStateFlow<Result<User>?>(null)
    val registerResponse: StateFlow<Result<User>?> = _registerResponse

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            _loginResponse.value = Result.Loading
            try {
                val user = authRepository.login(loginRequest)
                _loginResponse.value = Result.Success(user)
                _currentUserId.value = user.user_id.toString()
            }catch (e: Exception){
                _loginResponse.value = Result.Error("Login failed: ${e.localizedMessage}")
            }
        }
    }

    fun register(registerRequest: RegisterRequest){
        viewModelScope.launch {
            _registerResponse.value = Result.Loading
            try {
                val user = authRepository.register(registerRequest)
                _registerResponse.value = Result.Success(user)
                _currentUserId.value = user.user_id.toString()
            }catch (e: Exception){
                _registerResponse.value = Result.Error("Register failed: ${e.localizedMessage}")
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            try {
                _registerResponse.value = null
                _loginResponse.value = null
                authRepository.logout()
            }catch (e: Exception){
                e.printStackTrace()
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
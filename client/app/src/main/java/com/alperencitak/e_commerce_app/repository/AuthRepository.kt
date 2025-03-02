package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.AuthApiService
import com.alperencitak.e_commerce_app.model.LoginRequest
import com.alperencitak.e_commerce_app.model.RegisterRequest
import com.alperencitak.e_commerce_app.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApiService,
    private val context: Context,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun login(loginRequest: LoginRequest): User{
        val user = api.login(loginRequest)
        dataStoreManager.saveUserId(user.user_id.toString())
        return user
    }

    suspend fun register(registerRequest: RegisterRequest): User{
        val user = api.register(registerRequest)
        dataStoreManager.saveUserId(user.user_id.toString())
        return user
    }

    suspend fun logout(){
        dataStoreManager.clearUserId()
    }

    suspend fun getCurrentUserId(): String?{
        return dataStoreManager.getUserId()
    }

}
package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.AuthApiService
import com.alperencitak.e_commerce_app.model.LoginRequest
import com.alperencitak.e_commerce_app.model.RegisterRequest
import com.alperencitak.e_commerce_app.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApiService,
    private val context: Context
) {

    suspend fun login(loginRequest: LoginRequest): User{
        return api.login(loginRequest)
    }

    suspend fun register(registerRequest: RegisterRequest): User{
        return api.register(registerRequest)
    }

}
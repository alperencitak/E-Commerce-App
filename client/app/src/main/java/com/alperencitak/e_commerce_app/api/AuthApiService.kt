package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.LoginRequest
import com.alperencitak.e_commerce_app.model.RegisterRequest
import com.alperencitak.e_commerce_app.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): User

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): User

}
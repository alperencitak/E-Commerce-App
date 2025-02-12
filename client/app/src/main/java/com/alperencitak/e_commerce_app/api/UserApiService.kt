package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    @GET("user/")
    suspend fun getAllUser(): List<User>

    @GET("user/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): User

}
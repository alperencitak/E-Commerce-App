package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {

    @GET("user/")
    suspend fun getAllUser(): List<User>

    @GET("user/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): User

    @DELETE("user/delete/{id}")
    suspend fun deleteById(
        @Path("id") id: Int
    )

    @PUT("user/update")
    suspend fun update(
        @Body user: User
    ): User

}
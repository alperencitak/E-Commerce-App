package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.Address
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApiService {

    @GET("address/{id}")
    suspend fun getAddressById(
        @Path("id") id: Int
    ): Address

    @GET("address/user/{user_id}")
    suspend fun getAddressesByUserId(
        @Path("user_id") user_id: Int
    ): List<Address>

    @POST("address/add")
    suspend fun add(@Body address: Address): Address

    @DELETE("address/delete/{id}")
    suspend fun deleteById(@Path("id") id: Int)

    @PUT("address/update")
    suspend fun update(@Body address: Address): Address

}
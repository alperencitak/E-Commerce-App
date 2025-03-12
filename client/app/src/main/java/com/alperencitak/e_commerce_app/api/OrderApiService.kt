package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.Order
import com.alperencitak.e_commerce_app.model.OrderRequest
import com.alperencitak.e_commerce_app.model.OrderResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderApiService {

    @GET("order/{id}")
    suspend fun getById(@Path("id") id: Int): Order

    @GET("order/user/{user_id}")
    suspend fun getByUserId(@Path("user_id") user_id: Int): List<Order>

    @GET("order/details/{user_id}")
    suspend fun getByUserIdWithJoin(@Path("user_id") user_id: Int): List<OrderResponse>

    @POST("order/add")
    suspend fun add(@Body orderRequest: OrderRequest): Order

    @DELETE("order/delete/{id}")
    suspend fun deleteById(@Path("id") id: Int)

    @PUT("order/update")
    suspend fun update(@Body order: Order): Order

}
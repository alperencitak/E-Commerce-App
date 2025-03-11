package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.OrderDetail
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OrderDetailApiService {

    @GET("order_detail/{id}")
    suspend fun getById(@Path("id") id: Int): OrderDetail

    @GET("order_detail/order/{order_id}")
    suspend fun getByOrderId(@Path("order_id") order_id: Int): List<OrderDetail>

    @GET("order_detail/product/{product_id}")
    suspend fun getByProductId(@Path("product_id") product_id: Int): List<OrderDetail>

    @POST("order_detail/add")
    suspend fun add(@Body order_detail: OrderDetail): OrderDetail

    @POST("order_detail/add-all")
    suspend fun addAll(@Body order_details: List<OrderDetail>): List<OrderDetail>

    @DELETE("order_detail/delete/{id}")
    suspend fun deleteById(@Path("id") id: Int)

    @PUT("order_detail/update")
    suspend fun update(@Body order_detail: OrderDetail): OrderDetail

}
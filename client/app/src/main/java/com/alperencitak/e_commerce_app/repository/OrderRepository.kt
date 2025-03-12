package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.OrderApiService
import com.alperencitak.e_commerce_app.model.Order
import com.alperencitak.e_commerce_app.model.OrderRequest
import com.alperencitak.e_commerce_app.model.OrderResponse
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: OrderApiService,
    private val context: Context
) {

    suspend fun fetchById(id: Int): Order{
        return api.getById(id)
    }

    suspend fun fetchByUserId(userId: Int): List<Order>{
        return api.getByUserId(userId)
    }

    suspend fun fetchByUserIdWithJoin(userId: Int): List<OrderResponse>{
        return api.getByUserIdWithJoin(userId)
    }

    suspend fun add(orderRequest: OrderRequest): Order {
        return api.add(orderRequest)
    }

    suspend fun deleteById(id: Int){
        return api.deleteById(id)
    }

    suspend fun update(order: Order): Order {
        return api.update(order)
    }

}
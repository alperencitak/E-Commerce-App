package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.OrderApiService
import com.alperencitak.e_commerce_app.model.Order
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

    suspend fun add(order: Order): Order {
        return api.add(order)
    }

    suspend fun deleteById(id: Int){
        return api.deleteById(id)
    }

    suspend fun update(order: Order): Order {
        return api.update(order)
    }

}
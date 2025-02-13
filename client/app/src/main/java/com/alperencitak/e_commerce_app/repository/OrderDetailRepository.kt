package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.OrderDetailApiService
import com.alperencitak.e_commerce_app.model.OrderDetail
import javax.inject.Inject

class OrderDetailRepository @Inject constructor(
    private val api: OrderDetailApiService,
    private val context: Context
) {

    suspend fun fetchById(id: Int): OrderDetail{
        return api.getById(id)
    }

    suspend fun fetchByOrderId(orderId: Int): List<OrderDetail>{
        return api.getByOrderId(orderId)
    }

    suspend fun fetchByProductId(productId: Int): List<OrderDetail>{
        return api.getByProductId(productId)
    }

    suspend fun add(orderDetail: OrderDetail): OrderDetail {
        return api.add(orderDetail)
    }

    suspend fun deleteById(id: Int){
        return api.deleteById(id)
    }

    suspend fun update(orderDetail: OrderDetail): OrderDetail {
        return api.update(orderDetail)
    }

}
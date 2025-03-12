package com.alperencitak.e_commerce_app.model

data class OrderResponse(
    val orderId: Int,
    val userId: Int,
    val total_amount: Double,
    val status: String,
    val order_date: String,
    val products: List<Product>
)
package com.alperencitak.e_commerce_app.model

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val pages: Int,
    val current_page: Int,
    val per_page: Int
)

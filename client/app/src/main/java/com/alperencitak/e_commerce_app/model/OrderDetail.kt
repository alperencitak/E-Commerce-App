package com.alperencitak.e_commerce_app.model

import java.math.BigDecimal

data class OrderDetail(
    val order_detail_id: Int,
    val order_id: Int,
    val product_id: Int,
    val quantity: Int,
    val price: BigDecimal
)

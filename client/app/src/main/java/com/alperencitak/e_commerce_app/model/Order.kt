package com.alperencitak.e_commerce_app.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val order_id: Int,
    val user_id: Int,
    val total_amount: BigDecimal,
    val status: String = "PENDING",
    val order_date: String
)

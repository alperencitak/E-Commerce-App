package com.alperencitak.e_commerce_app.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderRequest(
    val user_id: Int,
    val total_amount: BigDecimal,
)

package com.alperencitak.e_commerce_app.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Product(
    val product_id: Int,
    val name: String,
    val price: BigDecimal,
    val stock: Int = 10,
    val category_id: Int,
    val created_at: LocalDateTime,
    val updated_at: LocalDateTime
)

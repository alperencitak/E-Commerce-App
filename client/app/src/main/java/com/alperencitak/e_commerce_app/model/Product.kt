package com.alperencitak.e_commerce_app.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Product(
    val product_id: Int,
    val name: String,
    val price: BigDecimal,
    val stock: Int,
    val image_url: String,
    val category_id: Int,
    val created_at: String,
    val updated_at: String
)

package com.alperencitak.e_commerce_app.model

data class User (
    val user_id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val created_at: String,
)
package com.alperencitak.e_commerce_app.model

data class RegisterRequest(
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val password: String
)

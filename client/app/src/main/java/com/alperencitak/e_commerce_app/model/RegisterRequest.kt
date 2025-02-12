package com.alperencitak.e_commerce_app.model

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val password: String
)

package com.alperencitak.e_commerce_app.model

data class Address(
    val address_id: Int = 0,
    val user_id : Int,
    val address_line1 : String,
    val address_line2 : String,
    val district : String,
    val city : String,
    val country : String
)

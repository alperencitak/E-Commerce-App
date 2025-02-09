package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.UserApiService
import com.alperencitak.e_commerce_app.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApiService,
    private val context: Context
) {

    suspend fun fetchUserById(id: Int): User{
        return api.getUserById(id=id)
    }

}
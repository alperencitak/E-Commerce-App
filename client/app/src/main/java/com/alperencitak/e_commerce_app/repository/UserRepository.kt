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

    suspend fun fetchAllUser(): List<User>{
        return api.getAllUser()
    }

    suspend fun deleteById(id: Int){
        return api.deleteById(id)
    }

    suspend fun update(user: User): User{
        return api.update(user)
    }

}
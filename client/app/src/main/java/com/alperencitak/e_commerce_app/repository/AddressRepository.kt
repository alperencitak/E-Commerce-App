package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.AddressApiService
import com.alperencitak.e_commerce_app.model.Address
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val api: AddressApiService,
    private val context: Context
) {

    suspend fun fetchAddressById(id: Int): Address{
        return api.getAddressById(id)
    }

    suspend fun fetchAddressByUserId(userId: Int): List<Address>{
        return api.getAddressesByUserId(userId)
    }

    suspend fun add(address: Address): Address{
        return api.add(address)
    }

    suspend fun deleteById(id: Int){
        return api.deleteById(id)
    }

    suspend fun update(address: Address): Address{
        return api.update(address)
    }

}
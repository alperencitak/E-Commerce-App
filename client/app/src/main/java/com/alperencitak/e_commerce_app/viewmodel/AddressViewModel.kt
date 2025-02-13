package com.alperencitak.e_commerce_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperencitak.e_commerce_app.model.Address
import com.alperencitak.e_commerce_app.repository.AddressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
): ViewModel() {

    private val _address = MutableStateFlow<Address?>(null)
    val address: StateFlow<Address?> = _address

    private val _addressList = MutableStateFlow<List<Address>>(emptyList())
    val addressList: StateFlow<List<Address>> = _addressList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchById(id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _address.value = addressRepository.fetchAddressById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchByUserId(user_id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _addressList.value = addressRepository.fetchAddressByUserId(user_id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }
    fun add(address: Address){
        viewModelScope.launch {
            try {
                _loading.value = true
                _address.value = addressRepository.add(address)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun deleteById(id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                addressRepository.deleteById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun update(address: Address){
        viewModelScope.launch {
            try {
                _loading.value = true
                _address.value = addressRepository.update(address)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }


}
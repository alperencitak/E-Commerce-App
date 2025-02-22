package com.alperencitak.e_commerce_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperencitak.e_commerce_app.model.OrderDetail
import com.alperencitak.e_commerce_app.repository.OrderDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderDetailRepository: OrderDetailRepository
): ViewModel() {

    private val _orderDetail = MutableStateFlow<OrderDetail?>(null)
    val orderDetail: StateFlow<OrderDetail?> = _orderDetail

    private val _orderDetailList = MutableStateFlow<List<OrderDetail>>(emptyList())
    val orderDetailList: StateFlow<List<OrderDetail>> = _orderDetailList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchById(id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _orderDetail.value = orderDetailRepository.fetchById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchByOrderId(order_id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _orderDetailList.value = orderDetailRepository.fetchByOrderId(order_id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchByProductId(product_id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _orderDetailList.value = orderDetailRepository.fetchByProductId(product_id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun add(orderDetail: OrderDetail){
        viewModelScope.launch {
            try {
                _loading.value = true
                _orderDetail.value = orderDetailRepository.add(orderDetail)
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
                orderDetailRepository.deleteById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun update(orderDetail: OrderDetail){
        viewModelScope.launch {
            try {
                _loading.value = true
                _orderDetail.value = orderDetailRepository.update(orderDetail)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }


}
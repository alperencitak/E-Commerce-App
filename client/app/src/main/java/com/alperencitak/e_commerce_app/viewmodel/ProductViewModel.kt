package com.alperencitak.e_commerce_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperencitak.e_commerce_app.model.Product
import com.alperencitak.e_commerce_app.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchById(id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _product.value = productRepository.fetchById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchByCategoryId(category_id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _productList.value = productRepository.fetchByCategoryId(category_id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }
    fun add(product: Product){
        viewModelScope.launch {
            try {
                _loading.value = true
                _product.value = productRepository.add(product)
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
                productRepository.deleteById(id)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun update(product: Product){
        viewModelScope.launch {
            try {
                _loading.value = true
                _product.value = productRepository.update(product)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }


}
package com.alperencitak.e_commerce_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperencitak.e_commerce_app.model.Product
import com.alperencitak.e_commerce_app.model.ProductResponse
import com.alperencitak.e_commerce_app.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    private val _productResponse = MutableStateFlow<ProductResponse?>(null)
    val productResponse: StateFlow<ProductResponse?> = _productResponse

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    private val _favoriteIds = MutableStateFlow<List<String>>(emptyList())
    val favoriteIds: StateFlow<List<String>> = _favoriteIds

    private val _favorites = MutableStateFlow<List<Product>>(emptyList())
    val favorites: StateFlow<List<Product>> = _favorites

    private val _cartIds = MutableStateFlow<List<String>>(emptyList())
    val cartIds: StateFlow<List<String>> = _cartIds

    private val _cart = MutableStateFlow<List<Product>>(emptyList())
    val cart: StateFlow<List<Product>> = _cart

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun updateProductList(list: List<Product>){
        viewModelScope.launch {
            try {
                _loading.value = true
                _productList.value += list
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

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

    fun fetchByCategoryId(category_id: Int, page: Int = 1, perPage: Int = 10){
        viewModelScope.launch {
            try {
                _loading.value = true
                _productResponse.value = productRepository.fetchByCategoryId(category_id, page, perPage)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchBestSellers(){
        viewModelScope.launch {
            try {
                _loading.value = true
                _productList.value = productRepository.fetchBestSellers()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun fetchRecommends(productId: Int, top: Int = 10){
        viewModelScope.launch {
            try {
                _loading.value = true
                _productList.value = productRepository.fetchRecommends(productId, top)
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

    fun getFavorites(favoriteIds: List<String>){
        viewModelScope.launch {
            try {
                _loading.value = true
                val favoriteList = mutableListOf<Product>()
                favoriteIds.forEach { productId ->
                    favoriteList.add(productRepository.fetchById(productId.toInt()))
                }
                _favorites.value = favoriteList
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun getFavoriteIds(){
        viewModelScope.launch {
            try {
                _loading.value = true
                _favoriteIds.value = productRepository.getFavorites()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun addFavorites(productId: String){
        viewModelScope.launch {
            try {
                _loading.value = true
                productRepository.addFavorites(productId)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
                getFavoriteIds()
            }
        }
    }

    fun removeFavorites(productId: String){
        viewModelScope.launch {
            try {
                _loading.value = true
                productRepository.removeFavorites(productId)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
                getFavoriteIds()
            }
        }
    }

    fun getCart(cartIds: List<String>){
        viewModelScope.launch {
            try {
                _loading.value = true
                val cartList = mutableListOf<Product>()
                cartIds.forEach { productId ->
                    cartList.add(productRepository.fetchById(productId.toInt()))
                }
                _cart.value = cartList
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun getCartIds(){
        viewModelScope.launch {
            try {
                _loading.value = true
                _cartIds.value = productRepository.getCart()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun addCart(productId: String){
        viewModelScope.launch {
            try {
                _loading.value = true
                productRepository.addCart(productId)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
                getCartIds()
            }
        }
    }

    fun removeCart(productId: String){
        viewModelScope.launch {
            try {
                _loading.value = true
                productRepository.removeCart(productId)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
                getCartIds()
            }
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            try {
                _loading.value = true
                productRepository.clearCart()
                _cartIds.value = emptyList()
                _cart.value = emptyList()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

}
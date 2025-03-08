package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.ProductApiService
import com.alperencitak.e_commerce_app.model.Product
import com.alperencitak.e_commerce_app.model.ProductResponse
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ProductApiService,
    private val context: Context,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun fetchById(id: Int): Product{
        return api.getById(id)
    }

    suspend fun fetchByCategoryId(categoryId: Int, page: Int = 1, perPage: Int = 10): ProductResponse{
        return api.getByCategoryId(categoryId, page, perPage)
    }

    suspend fun fetchBestSellers(): List<Product>{
        return api.getBestSellers()
    }

    suspend fun add(product: Product): Product {
        return api.add(product)
    }

    suspend fun deleteById(id: Int){
        return api.deleteById(id)
    }

    suspend fun update(product: Product): Product {
        return api.update(product)
    }

    suspend fun getFavorites(): List<String>{
        return dataStoreManager.getFavorites()
    }

    suspend fun addFavorites(productId: String){
        return dataStoreManager.addFavorite(productId)
    }

    suspend fun removeFavorites(productId: String){
        return dataStoreManager.removeFavorites(productId)
    }

    suspend fun getCart(): List<String>{
        return dataStoreManager.getCart()
    }

    suspend fun addCart(productId: String){
        return dataStoreManager.addCart(productId)
    }

    suspend fun removeCart(productId: String){
        return dataStoreManager.removeCart(productId)
    }

}
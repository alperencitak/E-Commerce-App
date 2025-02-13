package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.ProductApiService
import com.alperencitak.e_commerce_app.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ProductApiService,
    private val context: Context
) {

    suspend fun fetchById(id: Int): Product{
        return api.getById(id)
    }

    suspend fun fetchByCategoryId(categoryId: Int): List<Product>{
        return api.getByCategoryId(categoryId)
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

}
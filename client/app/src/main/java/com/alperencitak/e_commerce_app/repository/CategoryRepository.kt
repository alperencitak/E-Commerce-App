package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.CategoryApiService
import com.alperencitak.e_commerce_app.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: CategoryApiService,
    private val context: Context
) {

    suspend fun fetchAll(): List<Category>{
        return api.getAll()
    }

    suspend fun fetchById(id: Int): Category{
        return api.getById(id)
    }

    suspend fun add(category: Category): Category {
        return api.add(category)
    }

    suspend fun deleteById(id: Int){
        return api.deleteById(id)
    }

    suspend fun update(category: Category): Category {
        return api.update(category)
    }

}
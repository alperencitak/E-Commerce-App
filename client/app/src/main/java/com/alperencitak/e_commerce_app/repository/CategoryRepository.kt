package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.CategoryApiService
import com.alperencitak.e_commerce_app.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val api: CategoryApiService,
    private val context: Context
) {

    suspend fun fetchAllParent(): List<Category>{
        return api.getAllParent()
    }

    suspend fun fetchChildByParentId(parent_id: Int): List<Category>{
        return api.getChildByParentId(parent_id)
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
package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.Category
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoryApiService {

    @GET("category/")
    suspend fun getAllParent(): List<Category>

    @GET("category/subcategory/{parent_id}")
    suspend fun getChildByParentId(@Path("parent_id") parent_id: Int): List<Category>

    @GET("category/{id}")
    suspend fun getById(@Path("id") id: Int): Category

    @POST("category/add")
    suspend fun add(@Body category: Category): Category

    @DELETE("category/delete/{id}")
    suspend fun deleteById(@Path("id") id: Int)

    @PUT("category/update")
    suspend fun update(@Body category: Category): Category

}
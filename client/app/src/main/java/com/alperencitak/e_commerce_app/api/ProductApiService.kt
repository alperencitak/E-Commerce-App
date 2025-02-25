package com.alperencitak.e_commerce_app.api

import com.alperencitak.e_commerce_app.model.Product
import com.alperencitak.e_commerce_app.model.ProductResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("product/{id}")
    suspend fun getById(@Path("id") id:Int): Product

    @GET("product/category/{category_id}")
    suspend fun getByCategoryId(
        @Path("category_id") category_id: Int,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 10
    ): ProductResponse

    @GET("product/bestsellers")
    suspend fun getBestSellers(): List<Product>

    @POST("product/add")
    suspend fun add(@Body product: Product): Product

    @DELETE("product/delete/{id}")
    suspend fun deleteById(@Path("id") id: Int)

    @PUT("product/update")
    suspend fun update(@Body product: Product): Product

}
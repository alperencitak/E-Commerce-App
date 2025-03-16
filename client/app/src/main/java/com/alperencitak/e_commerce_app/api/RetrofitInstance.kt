package com.alperencitak.e_commerce_app.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:5000/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
            )
            .build()
    }

    val userApi: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    val addressApi: AddressApiService by lazy {
        retrofit.create(AddressApiService::class.java)
    }

    val authApi: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val categoryApi: CategoryApiService by lazy {
        retrofit.create(CategoryApiService::class.java)
    }

    val productApi: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    val orderApi: OrderApiService by lazy {
        retrofit.create(OrderApiService::class.java)
    }

    val orderDetailApi: OrderDetailApiService by lazy {
        retrofit.create(OrderDetailApiService::class.java)
    }

    val chatBotApi: ChatBotService by lazy {
        retrofit.create(ChatBotService::class.java)
    }

}
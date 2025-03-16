package com.alperencitak.e_commerce_app

import android.app.Application
import android.content.Context
import com.alperencitak.e_commerce_app.api.AddressApiService
import com.alperencitak.e_commerce_app.api.AuthApiService
import com.alperencitak.e_commerce_app.api.CategoryApiService
import com.alperencitak.e_commerce_app.api.ChatBotService
import com.alperencitak.e_commerce_app.api.OrderApiService
import com.alperencitak.e_commerce_app.api.OrderDetailApiService
import com.alperencitak.e_commerce_app.api.ProductApiService
import com.alperencitak.e_commerce_app.api.UserApiService
import com.alperencitak.e_commerce_app.api.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(app: Application): Context{
        return app.applicationContext
    }

    @Provides
    fun provideUserApiService(): UserApiService{
        return RetrofitInstance.userApi
    }

    @Provides
    fun provideAddressApiService(): AddressApiService{
        return RetrofitInstance.addressApi
    }

    @Provides
    fun provideAuthApiService(): AuthApiService{
        return RetrofitInstance.authApi
    }

    @Provides
    fun provideCategoryApiService(): CategoryApiService{
        return RetrofitInstance.categoryApi
    }

    @Provides
    fun provideProductApiService(): ProductApiService{
        return RetrofitInstance.productApi
    }

    @Provides
    fun provideOrderApiService(): OrderApiService{
        return RetrofitInstance.orderApi
    }

    @Provides
    fun provideOrderDetailApiService(): OrderDetailApiService{
        return RetrofitInstance.orderDetailApi
    }

    @Provides
    fun provideChatBotApiService(): ChatBotService{
        return RetrofitInstance.chatBotApi
    }

}
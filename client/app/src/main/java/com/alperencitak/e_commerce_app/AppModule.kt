package com.alperencitak.e_commerce_app

import android.app.Application
import android.content.Context
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
    fun provideApiService(): UserApiService{
        return RetrofitInstance.api
    }

}
package com.alperencitak.e_commerce_app

import android.app.Application
import com.alperencitak.e_commerce_app.repository.UserRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var userRepository: UserRepository

}
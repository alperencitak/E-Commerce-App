package com.alperencitak.e_commerce_app.repository

import android.content.Context
import com.alperencitak.e_commerce_app.api.ChatBotService
import javax.inject.Inject

class ChatBotRepository @Inject constructor(
    private val api: ChatBotService,
    private val context: Context
) {

    suspend fun ask(message: String): String{
        return api.ask(message)
    }

}
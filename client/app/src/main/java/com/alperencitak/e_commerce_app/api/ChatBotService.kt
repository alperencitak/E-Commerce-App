package com.alperencitak.e_commerce_app.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ChatBotService {

    @GET("/assistant/ask")
    suspend fun ask(
        @Query("message") message: String
    ): String

}
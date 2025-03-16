package com.alperencitak.e_commerce_app.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperencitak.e_commerce_app.model.Message
import com.alperencitak.e_commerce_app.repository.ChatBotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val chatBotRepository: ChatBotRepository
): ViewModel() {

    private val _messageList = mutableStateListOf<Message>()
    val messageList: List<Message> = _messageList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun ask(message: String){
        viewModelScope.launch {
            _loading.value = true
            val userMessage = Message(message, true)
            _messageList.add(userMessage)
            var aiText = ""
            try {
                aiText = chatBotRepository.ask(message)
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                val aiMessage = Message(aiText, false)
                _messageList.add(aiMessage)
                _loading.value = false
            }
        }
    }

}
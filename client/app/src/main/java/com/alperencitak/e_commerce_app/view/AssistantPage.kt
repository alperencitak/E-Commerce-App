package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.Blue
import com.alperencitak.e_commerce_app.ui.theme.DarkPurple
import com.alperencitak.e_commerce_app.ui.theme.White
import com.alperencitak.e_commerce_app.viewmodel.ChatBotViewModel


@Composable
fun AssistantPage(navHostController: NavHostController) {
    val chatBotViewModel: ChatBotViewModel = hiltViewModel()
    val messageList = chatBotViewModel.messageList
    val loading = chatBotViewModel.loading.collectAsState()
    var userMessage by remember { mutableStateOf("") }
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkPurple)
                .padding(top = 64.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                tint = White,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navHostController.popBackStack()
                    }
            )
            Text(
                text = "Assistant Bot",
                fontFamily = font,
                fontSize = 19.sp,
                color = White,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        if(messageList.isEmpty()){
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp, vertical = 16.dp),
                 horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "You can indicate below the topics you would like the assistant bot to help you with.",
                    fontFamily = font,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(messageList) { index, message ->
                if (message.isUser) {
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 128.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkPurple
                        )
                    ) {
                        Text(
                            text = message.text,
                            fontFamily = font,
                            fontSize = 16.sp,
                            color = White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                } else {
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 128.dp, top = 8.dp, bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray
                        )
                    ) {
                        Text(
                            text = message.text,
                            fontFamily = font,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                if (loading.value && index == messageList.size - 1) {
                    OutlinedCard(
                        modifier = Modifier
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.LightGray
                        )
                    ) {
                        Text(
                            text = "...",
                            fontFamily = font,
                            fontSize = 17.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userMessage,
                onValueChange = {
                    if (it.length < 512) {
                        userMessage = it
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 16.dp),
                enabled = !loading.value
            )
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                onClick = {
                    chatBotViewModel.ask(userMessage)
                    userMessage = ""
                },
                enabled = !loading.value
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Icon",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
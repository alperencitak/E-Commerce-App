package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.LightGray
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel

@Composable
fun HomePage(navHostController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val user = userViewModel.user.collectAsState()
    userViewModel.fetchUserById(101)
    Column(
        modifier = Modifier.fillMaxSize()
            .background(LightCream)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(LightGray, SoftBeige)
                    )
                )
        ){
            Column(
                modifier = Modifier.padding(top = 64.dp, bottom = 64.dp, start = 32.dp, end = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "HOME", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "\nLorem Ipsum is simply dummy text of the printing " +
                            "and typesetting industry. Lorem Ipsum has been the " +
                            "industry's standard dummy text ever since the 1500s, when an",
                    fontSize = 16.sp
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(LightGray)
                .padding(vertical = 8.dp)
        ) {
            items(10){
                ElevatedCard(
                    modifier = Modifier.aspectRatio(2.75f/3f).padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "Test Image"
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val text1 = remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 16.dp).weight(0.6f),
                value = text1.value,
                label = { (Text(text = "Test 1")) },
                onValueChange = { text1.value = it },
                shape = RoundedCornerShape(12.dp)
            )
            val text2 = remember { mutableStateOf(TextFieldValue("")) }
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 16.dp).weight(1f),
                value = text2.value,
                label = { (Text(text = "Test 2")) },
                onValueChange = { text2.value = it },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}
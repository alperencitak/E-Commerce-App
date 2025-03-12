package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.DarkGreen
import com.alperencitak.e_commerce_app.ui.theme.DarkPurple
import com.alperencitak.e_commerce_app.ui.theme.LightGray
import com.alperencitak.e_commerce_app.ui.theme.Purple
import com.alperencitak.e_commerce_app.ui.theme.White
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel
import com.alperencitak.e_commerce_app.viewmodel.OrderViewModel


@Composable
fun MyOrdersPage(navHostController: NavHostController) {
    val orderViewModel: OrderViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val orderResponseList = orderViewModel.orderResponseList.collectAsState()
    val currentUserId = authViewModel.currentUserId.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    authViewModel.getCurrentUserId()
    LaunchedEffect(currentUserId.value) {
        currentUserId.value?.let {
            orderViewModel.fetchByUserIdWithJoin(it.toInt())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
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
                text = "My Orders",
                fontFamily = font,
                fontSize = 19.sp,
                color = White,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        if(orderResponseList.value.isNotEmpty()){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                items(orderResponseList.value) { orderResponse ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(8.dp),
                                ambientColor = Purple,
                                spotColor = Purple
                            ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth().padding(8.dp).aspectRatio(29f/9f)
                        ) {
                            items(orderResponse.products){ product ->
                                Image(
                                    painter = rememberAsyncImagePainter("http://10.0.2.2:5000/image${product.image_url}"),
                                    contentDescription = "Product Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .aspectRatio(1f)
                                        .padding(4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, LightGray, RoundedCornerShape(8.dp))
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = orderResponse.status,
                                fontSize = 14.sp,
                                fontFamily = font,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                            )
                            Column(
                                modifier = Modifier.padding(end = 8.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                val date = orderResponse.order_date.split(" ")
                                Text(
                                    text = "${date[1]} ${date[2]} ${date[3]}",
                                    fontSize = 14.sp,
                                    fontFamily = font,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = "${orderResponse.total_amount} TL",
                                    fontSize = 15.sp,
                                    color = DarkGreen,
                                    fontFamily = font,
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
            }
        }else{
            Text(
                text = "You don't have any orders.",
                fontSize = 17.sp,
                fontFamily = font,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 32.dp, horizontal = 32.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
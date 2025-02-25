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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.LightGray
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel

@Composable
fun HomePage(navHostController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val productViewModel: ProductViewModel = hiltViewModel()
    val productList = productViewModel.productList.collectAsState()
    val user = userViewModel.user.collectAsState()
    val font = FontFamily(
        Font(
            R.font.montserrat_bold
        )
    )
    productViewModel.fetchBestSellers()
    userViewModel.fetchUserById(101)
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCream)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "E-Commerce App",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = font,
            modifier = Modifier.padding(top = 64.dp, bottom = 32.dp, start = 32.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "Test/Türkiye, Test mahallesi Test sokak No:1 Daire:1",
                    fontSize = 14.sp,
                    fontFamily = font,
                    minLines = 1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }
        }
        ScrollableCard()
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightGray)
                .padding(vertical = 8.dp)
        ) {
            items(productList.value) { product ->
                ElevatedCard(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column {
                        Image(
                            painter = rememberAsyncImagePainter("http://10.0.2.2:5000/image${product.image_url}"),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                        Text(
                            text = product.name,
                            maxLines = 2,
                            minLines = 2,
                            lineHeight = 15.sp,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 13.sp,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                top = 4.dp,
                                bottom = 12.dp,
                                start = 4.dp,
                                end = 4.dp
                            )
                        )
                        Text(
                            text = "${product.price} TL",
                            fontSize = 15.sp,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(LightGray, SoftBeige)
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 64.dp,
                    bottom = 64.dp,
                    start = 32.dp,
                    end = 32.dp
                ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "HOME", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "\nLorem Ipsum is simply dummy text of the printing " +
                            "and typesetting industry. Lorem Ipsum has been the " +
                            "industry's standard dummy text ever since the 1500s, when an" +
                            "and typesetting industry. Lorem Ipsum has been the " +
                            "industry's standard dummy text ever since the 1500s, when an" +
                            "and typesetting industry. Lorem Ipsum has been the " +
                            "industry's standard dummy text ever since the 1500s, when an" +
                            "and typesetting industry. Lorem Ipsum has been the " +
                            "industry's standard dummy text ever since the 1500s, when an" +
                            "and typesetting industry. Lorem Ipsum has been the " +
                            "industry's standard dummy text ever since the 1500s, when an",
                    fontSize = 16.sp,
                    fontFamily = font
                )
            }
        }
    }
}


@Composable
fun ScrollableCard() {
    val pagerState = rememberPagerState { 4 }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = CutCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().aspectRatio(16f/9f)
            ) { page ->
                Image(
                    painter = painterResource(R.drawable.test),
                    contentDescription = "Test $page",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.Gray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}
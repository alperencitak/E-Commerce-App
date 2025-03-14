package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.DarkPurple
import com.alperencitak.e_commerce_app.ui.theme.LightPurple
import com.alperencitak.e_commerce_app.ui.theme.White
import com.alperencitak.e_commerce_app.utils.SmallProductCard
import com.alperencitak.e_commerce_app.viewmodel.AddressViewModel
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomePage(navHostController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val productViewModel: ProductViewModel = hiltViewModel()
    val addressViewModel: AddressViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUserId = authViewModel.currentUserId.collectAsState()
    val address = addressViewModel.address.collectAsState()
    val productList = productViewModel.productList.collectAsState()
    val productResponse = productViewModel.productResponse.collectAsState()
    val user = userViewModel.user.collectAsState()
    val font = FontFamily(
        Font(
            R.font.montserrat_bold
        )
    )
    val campaignImages = listOf(
        R.drawable.campaign2,
        R.drawable.campaign3,
        R.drawable.campaign1,
        R.drawable.campaign4,
    )
    authViewModel.getCurrentUserId()
    productViewModel.fetchBestSellers()
    productViewModel.fetchByCategoryId(6)
    currentUserId.value?.let {
        userViewModel.fetchUserById(it.toInt())
    }
    user.value?.let {
        addressViewModel.fetchById(it.current_address_id)
    }
    var addressText = ""
    address.value?.let {
        if (it.city != null || it.country != null) {
            addressText =
                "${address.value!!.city}/${address.value!!.country}, ${address.value!!.address_line2}"
        } else {
            addressText = "No registered address."
        }
    }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
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
                .background(DarkPurple)
                .padding(horizontal = 16.dp, vertical = 24.dp), shape = RoundedCornerShape(8.dp)
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
                    text = addressText,
                    fontSize = 14.sp,
                    fontFamily = font,
                    minLines = 1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }
        }
        ScrollableCard(campaignImages)
        Text(
            text = "Best Sellers",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 16.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            items(productList.value) { product ->
                SmallProductCard(product, navHostController)
            }
        }
        productResponse.value?.let {
            Text(
                text = "Laptops",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(top = 8.dp, bottom = 16.dp)
            ) {
                items(it.products) { product ->
                    SmallProductCard(product, navHostController)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(White, LightPurple)
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 32.dp, bottom = 64.dp, start = 32.dp, end = 32.dp
                ), verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(512.dp))
            }
        }
    }
}


@Composable
fun ScrollableCard(campaignImages: List<Int>) {
    val pagerState = rememberPagerState { campaignImages.size }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while(true){
            delay(6000)
            coroutineScope.launch {
                val nextPage = (pagerState.currentPage + 1) % campaignImages.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CutCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) { page ->
                Image(
                    painter = painterResource(campaignImages[page]),
                    contentDescription = "$page",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp), horizontalArrangement = Arrangement.Center
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
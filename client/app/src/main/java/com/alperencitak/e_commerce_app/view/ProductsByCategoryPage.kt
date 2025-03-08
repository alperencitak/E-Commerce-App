package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.model.Product
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.utils.SmallProductCard
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel


@Composable
fun ProductsByCategoryPage(navHostController: NavHostController, categoryId: Int) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val productResponse = productViewModel.productResponse.collectAsState()
    val productList = productViewModel.productList.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    var page by remember { mutableIntStateOf(1) }
    var isLoadingMore by remember { mutableStateOf(false) }

    LaunchedEffect(page) {
        if (!isLoadingMore) {
            isLoadingMore = true
            productViewModel.fetchByCategoryId(category_id = categoryId, page = page)
            productResponse.value?.let {
                productViewModel.updateProductList(it.products)
            }
            isLoadingMore = false
        }
    }

    if (productResponse.value != null) {
        Column(
            modifier = Modifier
                .background(LightCream)
                .padding(top = 64.dp, start = 8.dp, end = 8.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Icon",
                tint = DarkerSoftBeige,
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 6.dp, bottom = 8.dp)
                    .clickable {
                        navHostController.popBackStack()
                    }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(productList.value) { product ->
                    SmallProductCard(product, navHostController)
                }
                item {
                    if (!isLoadingMore) {
                        LaunchedEffect(Unit) {
                            page++
                        }
                    }
                }
            }
        }
    }
}
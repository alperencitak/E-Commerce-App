package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel


@Composable
fun FavoritesPage(navHostController: NavHostController){
    val productViewModel: ProductViewModel = hiltViewModel()
    val favoriteIds = productViewModel.favoriteIds.collectAsState()
    val favorites = productViewModel.favorites.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    productViewModel.getFavoriteIds()
    productViewModel.getFavorites(favoriteIds.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCream),
    ) {
        Text(
            text = "Favorites",
            fontFamily = font,
            fontSize = 19.sp,
            color = DarkerSoftBeige,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 128.dp)
        ) {
            items(favorites.value) { product ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                        .clickable {
                            navHostController.navigate("product/${product.product_id}")
                        },
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("http://10.0.2.2:5000/image${product.image_url}"),
                        contentDescription = "Product Image",
                        contentScale = ContentScale.Crop,
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
}
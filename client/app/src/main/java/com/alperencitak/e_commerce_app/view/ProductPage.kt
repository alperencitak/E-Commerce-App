package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel


@Composable
fun ProductPage(navHostController: NavHostController, productId: Int) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val product = productViewModel.product.collectAsState()
    val favorites = productViewModel.favoriteIds.collectAsState()
    val cartIds = productViewModel.cartIds.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    productViewModel.getFavoriteIds()
    productViewModel.getCartIds()
    productViewModel.fetchById(productId)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCream)
            .padding(top = 64.dp)
    ) {
        product.value?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = DarkerSoftBeige,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            navHostController.popBackStack()
                        }
                )
            }
            Image(
                painter = rememberAsyncImagePainter("http://10.0.2.2:5000/image${it.image_url}"),
                contentDescription = "Product Image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Text(
                text = it.name,
                lineHeight = 15.sp,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
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
                text = "${it.price} TL",
                fontSize = 17.sp,
                fontFamily = font,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
            )
            Icon(
                imageVector = if (favorites.value.contains(productId.toString())) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite Icon",
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp).clickable {
                    val id = productId.toString()
                    if(favorites.value.contains(id)){
                        productViewModel.removeFavorites(id)
                    }else{
                        productViewModel.addFavorites(id)
                    }
                }
            )
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Cart Icon",
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp).clickable {
                    val id = productId.toString()
                    if(!cartIds.value.contains(id)){
                        productViewModel.addCart(id)
                    }
                }
            )
        }
    }
}
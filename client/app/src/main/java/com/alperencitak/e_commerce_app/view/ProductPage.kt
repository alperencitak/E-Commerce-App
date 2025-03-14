package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.DarkPurple
import com.alperencitak.e_commerce_app.ui.theme.Gray
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.Purple
import com.alperencitak.e_commerce_app.ui.theme.SmoothRed
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.ui.theme.White
import com.alperencitak.e_commerce_app.utils.SmallProductCard
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel


@Composable
fun ProductPage(navHostController: NavHostController, productId: Int) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val productList = productViewModel.productList.collectAsState()
    val product = productViewModel.product.collectAsState()
    val favorites = productViewModel.favoriteIds.collectAsState()
    val cartIds = productViewModel.cartIds.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    productViewModel.getFavoriteIds()
    productViewModel.getCartIds()
    productViewModel.fetchById(productId)
    productViewModel.fetchRecommends(productId)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 128.dp)
            .zIndex(1f),
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            modifier = Modifier.clickable {
                val id = productId.toString()
                if (favorites.value.contains(id)) {
                    productViewModel.removeFavorites(id)
                } else {
                    productViewModel.addFavorites(id)
                }
            }
        ) {
            Icon(
                imageVector = if (favorites.value.contains(productId.toString())) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite Icon",
                tint = if (favorites.value.contains(productId.toString())) Color.Red else Gray,
                modifier = Modifier
                    .size(36.dp)
                    .padding(4.dp)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCream)
    ) {
        product.value?.let {
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkPurple)
                    .padding(top = 64.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
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
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                Image(
                    painter = rememberAsyncImagePainter("http://10.0.2.2:5000/image${it.image_url}"),
                    contentDescription = "Product Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = it.name,
                    lineHeight = 15.sp,
                    fontSize = 17.sp,
                    fontFamily = font,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                )
                Text(
                    text = "Stock: ${it.stock}",
                    lineHeight = 15.sp,
                    fontSize = 16.sp,
                    fontFamily = font,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                )
                Row(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "This product has been received by ",
                        lineHeight = 15.sp,
                        fontSize = 16.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "${it.sales_amount}",
                        lineHeight = 15.sp,
                        fontSize = 17.sp,
                        color = Purple,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = " people",
                        lineHeight = 15.sp,
                        fontSize = 16.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                    )
                }
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .shadow(
                            elevation = 8.dp,
                            ambientColor = Purple,
                            spotColor = Purple
                        ),
                    shape = RectangleShape,
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(
                        text = "Campaigns",
                        lineHeight = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 17.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {}
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star Icon",
                                tint = Color.Yellow,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = "Buy 3 Pay 2",
                                fontSize = 14.sp,
                                fontFamily = font,
                                minLines = 1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Arrow Icon",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {}
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingCart,
                                contentDescription = "Shopping Cart Icon",
                                tint = SoftBeige,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                            Text(
                                text = "Free shipping over 250 TL, seller pays",
                                fontSize = 14.sp,
                                fontFamily = font,
                                minLines = 1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Gray,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Arrow Icon",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }
                    }
                }
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .shadow(
                            elevation = 8.dp,
                            ambientColor = Purple,
                            spotColor = Purple
                        ),
                    shape = RectangleShape,
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(
                        text = "Similar Products",
                        lineHeight = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 17.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 16.dp)
                    ) {
                        items(productList.value) { product ->
                            SmallProductCard(product, navHostController)
                        }
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 28.dp),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${it.price} TL",
                        fontSize = 17.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
                    )
                    Button(
                        onClick = {
                            val id = productId.toString()
                            if (!cartIds.value.contains(id)) {
                                productViewModel.addCart(id)
                                navHostController.navigate("cart")
                            }
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = DarkPurple,
                        ),
                        enabled = it.stock > 0
                    ) {
                        if (it.stock < 1) {
                            Text(
                                text = "Out of stock",
                                fontSize = 18.sp,
                                fontFamily = font,
                                color = SmoothRed,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
                            )
                        } else {
                            Text(
                                text = "Add to cart",
                                fontSize = 18.sp,
                                fontFamily = font,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
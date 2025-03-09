package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.alperencitak.e_commerce_app.ui.theme.Blue
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.Gray
import com.alperencitak.e_commerce_app.ui.theme.LightBlue
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.LightGray
import com.alperencitak.e_commerce_app.ui.theme.SmoothRed
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.utils.Dialog
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel


@Composable
fun CartPage(navHostController: NavHostController, paddingValues: PaddingValues){
    val productViewModel: ProductViewModel = hiltViewModel()
    val cartIds = productViewModel.cartIds.collectAsState()
    val cart = productViewModel.cart.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }
    var totalPrice: Double = cart.value.sumOf { it.price.toDouble() }
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    productViewModel.getCartIds()
    productViewModel.getCart(cartIds.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCream),
    ) {
        Text(
            text = "Cart (${cartIds.value.size} Product)",
            fontFamily = font,
            fontSize = 18.sp,
            color = DarkerSoftBeige,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            items(cart.value) { product ->
                if(openAlertDialog.value){
                    Dialog(
                        icon = Icons.Outlined.Delete,
                        onDismissRequest = {
                            openAlertDialog.value = false
                        },
                        onConfirmation = {
                            openAlertDialog.value = false
                            productViewModel.removeCart(product.product_id.toString())
                        },
                        dialogTitle = "Remove From Cart",
                        dialogText = "Are you sure you want to remove this item from your cart?"
                    )
                }
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .clickable {
                                navHostController.navigate("product/${product.product_id}")
                            },
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter("http://10.0.2.2:5000/image${product.image_url}"),
                            contentDescription = "Product Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, LightGray, RoundedCornerShape(8.dp))
                        )
                        Text(
                            text = product.name,
                            maxLines = 3,
                            minLines = 3,
                            lineHeight = 15.sp,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 13.sp,
                            fontFamily = font,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(2f).padding(
                                top = 12.dp,
                                bottom = 12.dp,
                                start = 4.dp,
                                end = 4.dp
                            )
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedCard(
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                            border = BorderStroke(2.dp, LightGray)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete Icon",
                                tint = SmoothRed,
                                modifier = Modifier.padding(4.dp).clickable {
                                    openAlertDialog.value = true
                                }
                            )
                        }
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
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = paddingValues.calculateBottomPadding() + 8.dp),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Total:",
                        fontSize = 16.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
                    )
                    Text(
                        text = "$totalPrice TL",
                        fontSize = 16.sp,
                        fontFamily = font,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
                    )
                }
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = DarkerSoftBeige,
                    )
                ) {
                    Text(text = "Confirm Cart", color = Color.White, fontFamily = font)
                }
            }
        }
    }
}
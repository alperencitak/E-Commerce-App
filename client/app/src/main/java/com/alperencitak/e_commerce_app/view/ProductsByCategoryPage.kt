package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
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
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel


@Composable
fun ProductsByCategoryPage(navHostController: NavHostController, categoryId: Int) {
    val productViewModel: ProductViewModel = hiltViewModel()
    val productList = productViewModel.productResponse.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    productViewModel.fetchByCategoryId(categoryId)
    println(categoryId)

    if (productList.value != null){
        val products = productList.value!!.products
        Column(
            modifier = Modifier.padding(top = 64.dp, start = 8.dp, end = 8.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Icon",
                tint = DarkerSoftBeige,
                modifier = Modifier.size(48.dp).padding(start = 6.dp, bottom = 8.dp).clickable {
                    navHostController.popBackStack()
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(products){ product ->
                    ElevatedCard(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp)
                            .clickable {  },
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
}
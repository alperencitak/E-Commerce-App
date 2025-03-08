package com.alperencitak.e_commerce_app.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.model.Product


@Composable
fun SmallProductCard(product: Product, navHostController: NavHostController) {
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    ElevatedCard(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clickable {
                navHostController.navigate("product/${product.product_id}")
            },
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
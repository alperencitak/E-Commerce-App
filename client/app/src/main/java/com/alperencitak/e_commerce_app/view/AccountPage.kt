package com.alperencitak.e_commerce_app.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.DarkPurple
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.LightPurple
import com.alperencitak.e_commerce_app.ui.theme.Purple
import com.alperencitak.e_commerce_app.ui.theme.SmoothPurple
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.ui.theme.White
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel


@Composable
fun AccountPage(navHostController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUserId = authViewModel.currentUserId.collectAsState()
    val user = userViewModel.user.collectAsState()
    val loading = userViewModel.loading.collectAsState()
    val font = FontFamily(
        Font(
            R.font.montserrat_bold
        )
    )
    authViewModel.getCurrentUserId()
    currentUserId.value?.let {
        userViewModel.fetchUserById(it.toInt())
    }
    if(user.value != null){
        Column(
            modifier = Modifier.fillMaxSize().background(White).padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account Icon",
                modifier = Modifier.size(175.dp).padding(bottom = 16.dp)
            )
            Text(
                text = user.value!!.run { "$first_name $last_name" },
                fontSize = 17.sp,
                fontFamily = font,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
            )
            ButtonRow(icon = Icons.Default.ShoppingCart, text = "My Orders"){
                navHostController.navigate("my_orders")
            }
            ButtonRow(icon = Icons.Default.ArrowDropDown, text = "My Coupons"){

            }
            ButtonRow(icon = Icons.Default.LocationOn, text = "My Address"){
                navHostController.navigate("address")
            }
            ButtonRow(icon = Icons.Default.Star, text = "Premium"){

            }
            ButtonRow(icon = Icons.Default.Settings, text = "Settings"){

            }
            ButtonRow(icon = Icons.Default.ThumbUp, text = "Application Feedback"){

            }
            ButtonRow(icon = Icons.Default.Call, text = "Customer Service"){

            }
            OutlinedButton(
                onClick = {
                    authViewModel.logout()
                    navHostController.navigate("login"){
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp, vertical = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = DarkPurple
                ),
                border = BorderStroke(1.dp, DarkPurple)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Log out",
                        fontSize = 16.sp,
                        fontFamily = font
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ButtonRow(icon: ImageVector, text: String,onClick: () -> Unit){
    val font = FontFamily(
        Font(
            R.font.montserrat_bold
        )
    )
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp).clickable {
            onClick()
        }
    ) {
        HorizontalDivider(
            thickness = 2.dp,
            color = LightPurple
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = text,
                fontSize = 15.sp,
                fontFamily = font,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
        }
    }
}
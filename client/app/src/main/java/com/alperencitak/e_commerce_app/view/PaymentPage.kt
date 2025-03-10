package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.DarkPurple
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.Purple
import com.alperencitak.e_commerce_app.ui.theme.White
import com.alperencitak.e_commerce_app.utils.LoadingBar
import com.alperencitak.e_commerce_app.viewmodel.AddressViewModel
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel
import com.alperencitak.e_commerce_app.viewmodel.ProductViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel


@Composable
fun PaymentPage(navHostController: NavHostController) {
    val addressViewModel: AddressViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val productViewModel: ProductViewModel = hiltViewModel()
    val currentUserId = authViewModel.currentUserId.collectAsState()
    val user = userViewModel.user.collectAsState()
    val address = addressViewModel.address.collectAsState()
    val addressList = addressViewModel.addressList.collectAsState()
    val cartIds = productViewModel.cartIds.collectAsState()
    val cart = productViewModel.cart.collectAsState()
    val totalPrice: Double = cart.value.sumOf { it.price.toDouble() }
    var expanded by remember { mutableStateOf(false) }
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    authViewModel.getCurrentUserId()
    currentUserId.value?.let {
        userViewModel.fetchUserById(it.toInt())
        addressViewModel.fetchByUserId(it.toInt())
    }
    user.value?.let {
        addressViewModel.fetchById(it.current_address_id)
    }
    productViewModel.getCartIds()
    productViewModel.getCart(cartIds.value)

    if (address.value != null && user.value != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkPurple)
                    .padding(top = 64.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
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
                Text(
                    text = "Payment",
                    fontFamily = font,
                    fontSize = 19.sp,
                    color = White,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(8.dp),
                        ambientColor = Purple,
                        spotColor = Purple
                    ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Text(
                    text = "Delivery Address",
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
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            expanded = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location Icon",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Text(
                            text = "${address.value!!.district}/${address.value!!.city}/${address.value!!.country}",
                            fontSize = 14.sp,
                            fontFamily = font,
                            minLines = 1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        addressList.value.forEachIndexed{ index, address ->
                            if(index != 0){
                                HorizontalDivider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "${address.district}/${address.city}/${address.country}",
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 14.sp,
                                        fontFamily = font,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                                    )
                                },
                                onClick = {
                                    expanded = false
                                    val updatedUser = user.value!!.copy(current_address_id = address.address_id)
                                    userViewModel.update(updatedUser)
                                }
                            )
                        }
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(8.dp),
                        ambientColor = Purple,
                        spotColor = Purple
                    ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Text(
                    text = "Payment Options",
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = true,
                        onCheckedChange = {

                        },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                    Text(
                        text = "Pay with Debit & Credit Card",
                        fontSize = 14.sp,
                        fontFamily = font,
                        minLines = 1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = {

                        },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                    Text(
                        text = "Pay with Account Balance",
                        fontSize = 14.sp,
                        fontFamily = font,
                        minLines = 1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                    )
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(8.dp),
                        ambientColor = Purple,
                        spotColor = Purple
                    ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Text(
                    text = "Card Information",
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
                OutlinedTextField(
                    value = "",
                    onValueChange = {
//                        if (it.length <= 100) {
//
//                        }
                    },
                    label = { Text("fullname", fontFamily = font) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
//                        unfocusedBorderColor = if () Color.Red else DarkerSoftBeige,
//                        focusedBorderColor = if () Color.Red else DarkerSoftBeige
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {
//                        if (it.length <= 100) {
//
//                        }
                    },
                    label = { Text("number", fontFamily = font) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
//                        unfocusedBorderColor = if () Color.Red else DarkerSoftBeige,
//                        focusedBorderColor = if () Color.Red else DarkerSoftBeige
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {
//                        if (it.length <= 100) {
//
//                        }
                        },
                        label = { Text("date", fontFamily = font) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
//                        unfocusedBorderColor = if () Color.Red else DarkerSoftBeige,
//                        focusedBorderColor = if () Color.Red else DarkerSoftBeige
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                    OutlinedTextField(
                        value = "",
                        onValueChange = {
//                        if (it.length <= 100) {
//
//                        }
                        },
                        label = { Text("security", fontFamily = font) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
//                        unfocusedBorderColor = if () Color.Red else DarkerSoftBeige,
//                        focusedBorderColor = if () Color.Red else DarkerSoftBeige
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp, bottom = 24.dp),
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
                            productViewModel.clearCart()
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = DarkPurple,
                        )
                    ) {
                        Text(text = "Confirm and Finish", color = Color.White, fontFamily = font)
                    }
                }
            }
        }
    }else{
        LoadingBar()
    }
}
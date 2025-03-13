package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.ui.theme.DarkGreen
import com.alperencitak.e_commerce_app.ui.theme.DarkPurple
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.White
import com.alperencitak.e_commerce_app.utils.Dialog
import com.alperencitak.e_commerce_app.viewmodel.AddressViewModel
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel


@Composable
fun AddressPage(navHostController: NavHostController) {
    val addressViewModel: AddressViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUserId = authViewModel.currentUserId.collectAsState()
    val user = userViewModel.user.collectAsState()
    val addressList = addressViewModel.addressList.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    authViewModel.getCurrentUserId()
    currentUserId.value?.let {
        userViewModel.fetchUserById(it.toInt())
        addressViewModel.fetchByUserId(it.toInt())
    }

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
                text = "My Addresses",
                fontFamily = font,
                fontSize = 19.sp,
                color = White,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        OutlinedButton(
            onClick = {
                navHostController.navigate("add_address")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, DarkPurple)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon",
                    tint = DarkPurple
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(addressList.value) { address ->
                if(addressList.value.size == 1 && user.value != null){
                    val updatedUser = user.value!!.copy(current_address_id = address.address_id)
                    userViewModel.update(updatedUser)
                }
                val openAlertDialog = remember { mutableStateOf(false) }
                if (openAlertDialog.value) {
                    Dialog(
                        onDismissRequest = {
                            openAlertDialog.value = false
                        },
                        onConfirmation = {
                            openAlertDialog.value = false
                            addressViewModel.deleteById(address.address_id)
                            navHostController.navigate("address"){
                                popUpTo("address") { inclusive = true }
                            }
                        },
                        dialogTitle = "Delete Selected Address",
                        dialogText = "Deletion cannot be undone. Are you sure?",
                        icon = Icons.Filled.Delete
                    )
                }
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable {
                            user.value?.let {
                                val updatedUser = it.copy(current_address_id = address.address_id)
                                userViewModel.update(updatedUser)
                            }
                        },
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    user.value?.let {
                        Row(
                            modifier = Modifier.padding(top = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (it.current_address_id == address.address_id) {
                                    Icon(
                                        imageVector = Icons.Default.Place,
                                        contentDescription = "",
                                        tint = DarkGreen,
                                        modifier = Modifier
                                            .padding(horizontal = 6.dp)
                                            .size(25.dp)
                                    )
                                    Text(
                                        text = "Current",
                                        fontFamily = font,
                                        fontSize = 15.sp,
                                        color = DarkGreen
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .size(25.dp)
                                    .clickable {
                                        openAlertDialog.value = true
                                    }
                            )
                        }
                    }
                    Text(
                        text = address.address_line1,
                        fontFamily = font,
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${address.city}/${address.country} - ${address.district}",
                        fontFamily = font,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        minLines = 1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = address.address_line2,
                        fontFamily = font,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
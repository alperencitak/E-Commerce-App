package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.LightGray
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.viewmodel.AddressViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel


@Composable
fun AddressPage(navHostController: NavHostController) {
    val addressViewModel: AddressViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val address = addressViewModel.address.collectAsState()
    val user = userViewModel.user.collectAsState()
    val addressList = addressViewModel.addressList.collectAsState()
    val loading = addressViewModel.loading.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    userViewModel.fetchUserById(101)
    addressViewModel.fetchByUserId(101)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCream),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                tint = DarkerSoftBeige,
                modifier = Modifier.size(32.dp).clickable {
                    navHostController.popBackStack()
                }
            )
            Text(
                text = "My Addresses",
                fontFamily = font,
                fontSize = 19.sp,
                color = DarkerSoftBeige,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 64.dp),
                textAlign = TextAlign.Center
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp, max = 400.dp)
        ) {
            items(addressList.value) { address ->
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
                        if(it.current_address_id == address.address_id){
                            Row(
                                modifier = Modifier.padding(top = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = "",
                                    tint = DarkGreen,
                                    modifier = Modifier.padding(horizontal = 6.dp).size(25.dp)
                                )
                                Text(
                                    text = "Current",
                                    fontFamily = font,
                                    fontSize = 15.sp,
                                    color = DarkGreen
                                )
                            }
                        }
                    }
                    Text(
                        text = address.address_line1,
                        fontFamily = font,
                        fontSize = 17.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
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
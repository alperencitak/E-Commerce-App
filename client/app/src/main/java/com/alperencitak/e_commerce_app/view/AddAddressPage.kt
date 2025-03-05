package com.alperencitak.e_commerce_app.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.model.Address
import com.alperencitak.e_commerce_app.ui.theme.Blue
import com.alperencitak.e_commerce_app.ui.theme.DarkGreen
import com.alperencitak.e_commerce_app.ui.theme.DarkerSoftBeige
import com.alperencitak.e_commerce_app.ui.theme.Gray
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.ui.theme.WhiteModern
import com.alperencitak.e_commerce_app.viewmodel.AddressViewModel
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel


@Composable
fun AddAddressPage(navHostController: NavHostController){
    val userViewModel: UserViewModel = hiltViewModel()
    val addressViewModel: AddressViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUserId = authViewModel.currentUserId.collectAsState()
    val user = userViewModel.user.collectAsState()
    val address = addressViewModel.address.collectAsState()
    val font = FontFamily(
        Font(R.font.montserrat_bold)
    )
    authViewModel.getCurrentUserId()
    currentUserId.value?.let {
        userViewModel.fetchUserById(it.toInt())
    }

    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var line1SelectedIndex by remember { mutableIntStateOf(0) }
    val line1 = listOf("Home Address", "Work Address", "Other Address")
    var line2 by remember { mutableStateOf("") }
    var countryError by remember { mutableStateOf(false) }
    var cityError by remember { mutableStateOf(false) }
    var districtError by remember { mutableStateOf(false) }
    var line2Error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightCream),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
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
            Text(
                text = "Add New Addresses",
                fontFamily = font,
                fontSize = 19.sp,
                color = DarkerSoftBeige,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 64.dp),
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = country,
                onValueChange = {
                    if (it.length <= 100) {
                        country = it
                    }
                },
                label = { Text("Country", fontFamily = font) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedBorderColor = if (countryError) Color.Red else DarkerSoftBeige,
                    focusedBorderColor = if (countryError) Color.Red else DarkerSoftBeige
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            OutlinedTextField(
                value = city,
                onValueChange = {
                    if (it.length <= 100) {
                        city = it
                    }
                },
                label = { Text("City", fontFamily = font) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedBorderColor = if (cityError) Color.Red else DarkerSoftBeige,
                    focusedBorderColor = if (cityError) Color.Red else DarkerSoftBeige
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = district,
                onValueChange = {
                    if (it.length <= 100) {
                        district = it
                    }
                },
                label = { Text("District", fontFamily = font) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedBorderColor = if (districtError) Color.Red else DarkerSoftBeige,
                    focusedBorderColor = if (districtError) Color.Red else DarkerSoftBeige
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
        }
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            line1.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = line1.size
                    ),
                    onClick = { line1SelectedIndex = index },
                    selected = index == line1SelectedIndex,
                    label = { Text(label, fontFamily = font) },
                    colors = SegmentedButtonDefaults.colors(
                        inactiveContainerColor = SoftBeige,
                        activeContainerColor = DarkerSoftBeige,
                        inactiveContentColor = WhiteModern,
                        activeContentColor = LightCream,
                        inactiveBorderColor = DarkerSoftBeige,
                        activeBorderColor = Color.Gray,
                    )
                )
            }
        }
        OutlinedTextField(
            value = line2,
            onValueChange = {
                if (it.length <= 255) {
                    line2 = it
                }
            },
            label = { Text("Line 2", fontFamily = font) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (line2Error) Color.Red else DarkerSoftBeige,
                focusedBorderColor = if (line2Error) Color.Red else DarkerSoftBeige
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(horizontal = 8.dp)
        )
        Button(
            onClick = {
                currentUserId.value?.let {
                    val address = Address(
                        user_id = it.toInt(),
                        address_line1 = line1[line1SelectedIndex],
                        address_line2 = line2,
                        district = district,
                        city = city,
                        country = country
                    )
                    val addedAddress = addressViewModel.add(address)
                    if(addedAddress != null){
                        navHostController.popBackStack()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGreen
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon"
                )
            }
        }
    }
}

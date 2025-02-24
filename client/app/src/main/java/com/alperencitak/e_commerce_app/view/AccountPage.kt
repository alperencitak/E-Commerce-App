package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel


@Composable
fun AccountPage(navHostController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val user = userViewModel.user.collectAsState()
    val loading = userViewModel.loading.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().background(LightCream)
    ) {

    }
}
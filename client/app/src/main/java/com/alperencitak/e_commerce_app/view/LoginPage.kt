package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel


@Composable
fun LoginPage(navHostController: NavHostController){

    val authViewModel: AuthViewModel = hiltViewModel()
    val loginResponse = authViewModel.loginResponse.collectAsState()
    val registerResponse = authViewModel.registerResponse.collectAsState()
    val loading = authViewModel.loading.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}
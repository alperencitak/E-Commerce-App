package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel


@Composable
fun SplashScreen(navHostController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUserId = authViewModel.currentUserId.collectAsState()

    LaunchedEffect(Unit) {
        authViewModel.getCurrentUserId()
    }

    LaunchedEffect(currentUserId.value) {
        currentUserId.value?.let {
            if (it.toInt() != 0) {
                navHostController.navigate("main") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                navHostController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

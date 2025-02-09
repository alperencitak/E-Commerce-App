package com.alperencitak.e_commerce_app.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.alperencitak.e_commerce_app.viewmodel.UserViewModel

@Composable
fun LoginPage() {
    val userViewModel: UserViewModel = hiltViewModel()
    val user = userViewModel.user.collectAsState()
    userViewModel.fetchUserById(101)
    user.value?.let {
        println(it.first_name)
    }
}
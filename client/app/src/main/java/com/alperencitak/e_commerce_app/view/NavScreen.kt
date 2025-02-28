package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable


@Composable
fun NavScreen(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = Modifier.fillMaxSize()
    ){
        composable("main"){
            HomePage(navController)
        }
        composable("login"){
            LoginPage(navController)
        }
        composable("categories"){
            CategoriesPage(navController)
        }
        composable("child_categories/{category_id}"){ backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("category_id")?.toIntOrNull() ?: 0
            ChildCategoriesPage(navController, categoryId)
        }
        composable("account"){
            AccountPage(navController)
        }
        composable("address"){
            AddressPage(navController)
        }
        composable("products_by_category/{category_id}"){ backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("category_id")?.toIntOrNull() ?: 0
            ProductsByCategoryPage(navController, categoryId)
        }
    }
}
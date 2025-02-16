package com.alperencitak.e_commerce_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alperencitak.e_commerce_app.ui.theme.ECommerceAppTheme
import com.alperencitak.e_commerce_app.ui.theme.SoftBeige
import com.alperencitak.e_commerce_app.view.NavScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceAppTheme {
                ScaffoldWithNavBar()
            }
        }
    }
}


@Composable
fun ScaffoldWithNavBar(){
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                val itemColor = SoftBeige
                NavigationBarItem(
                    selected = currentRoute == "main",
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home Icon") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = itemColor,
                        selectedIconColor = Color.White,
                        unselectedIconColor = itemColor,
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == "search",
                    onClick = {},
                    icon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = itemColor,
                        selectedIconColor = Color.White,
                        unselectedIconColor = itemColor,
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == "favorites",
                    onClick = {},
                    icon = { Icon(Icons.Default.Star, contentDescription = "Star Icon") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = itemColor,
                        selectedIconColor = Color.White,
                        unselectedIconColor = itemColor,
                    )
                )
                NavigationBarItem(
                    selected = currentRoute == "account",
                    onClick = {},
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Account Icon") },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = itemColor,
                        selectedIconColor = Color.White,
                        unselectedIconColor = itemColor,
                    )
                )
            }
        }
    ){ paddingValues ->
       NavScreen(navController, paddingValues)
    }
}

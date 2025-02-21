package com.alperencitak.e_commerce_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alperencitak.e_commerce_app.ui.theme.Blue
import com.alperencitak.e_commerce_app.ui.theme.ECommerceAppTheme
import com.alperencitak.e_commerce_app.ui.theme.Gray
import com.alperencitak.e_commerce_app.ui.theme.LightBlue
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
fun ScaffoldWithNavBar() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val barRoutes = listOf("main", "search")
    Scaffold(
        bottomBar = {
            if (barRoutes.contains(currentRoute)) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 4.dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                ) {
                    val itemColor = LightBlue
                    NavigationBarItem(
                        selected = currentRoute == "main",
                        onClick = {},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.home),
                                contentDescription = "Home Icon"
                            )
                        },
                        label = { Text(text = "Home", color = LightBlue) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = itemColor,
                            selectedIconColor = Color.White,
                            unselectedIconColor = itemColor,
                        )
                    )
                    NavigationBarItem(
                        selected = currentRoute == "categories",
                        onClick = {},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.categories),
                                contentDescription = "Categories Icon"
                            )
                        },
                        label = { Text(text = "Categories", color = LightBlue) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = itemColor,
                            selectedIconColor = Color.White,
                            unselectedIconColor = itemColor,
                        ),
                        modifier = Modifier.offset(y = (-16).dp)
                    )
                    NavigationBarItem(
                        selected = currentRoute == "favorites",
                        onClick = {},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.favorite),
                                contentDescription = "Favorite Icon"
                            )
                        },
                        label = { Text(text = "Favorites", color = LightBlue) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = itemColor,
                            selectedIconColor = Color.White,
                            unselectedIconColor = itemColor,
                        ),
                        modifier = Modifier.offset(y = (-32).dp).padding(top = 24.dp)
                    )
                    NavigationBarItem(
                        selected = currentRoute == "cart",
                        onClick = {},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.cart),
                                contentDescription = "Cart Icon"
                            )
                        },
                        label = { Text(text = "  Cart", color = LightBlue) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = itemColor,
                            selectedIconColor = Color.White,
                            unselectedIconColor = itemColor,
                        ),
                        modifier = Modifier.offset(y = (-16).dp)
                    )
                    NavigationBarItem(
                        selected = currentRoute == "account",
                        onClick = {},
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.account),
                                contentDescription = "Account Icon"
                            )
                        },
                        label = { Text(text = "Account", color = LightBlue) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = itemColor,
                            selectedIconColor = Color.White,
                            unselectedIconColor = itemColor,
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        NavScreen(navController, paddingValues)
    }
}

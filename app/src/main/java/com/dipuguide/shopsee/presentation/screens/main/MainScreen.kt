package com.dipuguide.shopsee.presentation.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dipuguide.shopsee.presentation.navigation.CategoriesRoute
import com.dipuguide.shopsee.presentation.navigation.HomeRoute
import com.dipuguide.shopsee.presentation.navigation.ItemCartRoute
import com.dipuguide.shopsee.presentation.navigation.MyMallRoute
import com.dipuguide.shopsee.presentation.navigation.OrderRoute
import com.dipuguide.shopsee.presentation.screens.main.categories.CategoriesScreen
import com.dipuguide.shopsee.presentation.screens.main.home.HomeScreen
import com.dipuguide.shopsee.presentation.screens.main.itemCart.ItemCartScreen
import com.dipuguide.shopsee.presentation.screens.main.myMall.MyMallScreen
import com.dipuguide.shopsee.presentation.screens.main.order.OrderScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val tabNavController = rememberNavController()

    NavHost(
        navController = tabNavController,
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomeScreen()
        }

        composable<CategoriesRoute> {
            CategoriesScreen()
        }

        composable<MyMallRoute> {
            MyMallScreen()
        }

        composable<OrderRoute> {
            OrderScreen()
        }
        composable<ItemCartRoute> {
            ItemCartScreen()
        }
    }

}
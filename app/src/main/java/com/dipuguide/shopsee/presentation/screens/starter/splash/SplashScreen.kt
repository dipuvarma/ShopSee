package com.dipuguide.shopsee.presentation.screens.starter.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dipuguide.shopsee.R
import com.dipuguide.shopsee.presentation.navigation.LocalNavController
import com.dipuguide.shopsee.presentation.navigation.MainRoute
import com.dipuguide.shopsee.presentation.navigation.OnBoardingRoute
import com.dipuguide.shopsee.presentation.screens.starter.onboarding.OnBoardingScreen

@Composable
fun SplashScreen() {

    val navController = LocalNavController.current
    val viewModel = hiltViewModel<SplashViewModel>()
    val splashAuthState by viewModel.splashAuthState.collectAsState()
    LaunchedEffect(splashAuthState) {
        when (splashAuthState){
            SplashAuthState.Authenticated -> {
                navController.navigate(MainRoute) {
                    popUpTo(0)
                }
            }

            SplashAuthState.Unauthenticated -> {
                navController.navigate(OnBoardingRoute) {
                    popUpTo(0)
                }
            }
            SplashAuthState.Loading -> {

            }
        }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.shop_see_logo),
            contentDescription = stringResource(R.string.app_name)
        )
    }
}
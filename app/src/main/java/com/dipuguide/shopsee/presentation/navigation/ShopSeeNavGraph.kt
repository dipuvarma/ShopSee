package com.dipuguide.shopsee.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dipuguide.shopsee.presentation.screens.main.MainScreen
import com.dipuguide.shopsee.presentation.screens.others.editProfile.EditProfileScreen
import com.dipuguide.shopsee.presentation.screens.others.profile.ProfileScreen
import com.dipuguide.shopsee.presentation.screens.starter.forgetpassword.ForgetPasswordScreen
import com.dipuguide.shopsee.presentation.screens.starter.onboarding.OnBoardingScreen
import com.dipuguide.shopsee.presentation.screens.starter.signIn.SignInScreen
import com.dipuguide.shopsee.presentation.screens.starter.signUp.SignUpScreen
import com.dipuguide.shopsee.presentation.screens.starter.splash.SplashScreen


val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("No NavController Provide")
}

@Composable
fun ShopSeeNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = SignUpRoute
        ) {
            composable<SplashRoute> {
                SplashScreen()
            }
            composable<SignInRoute> {
                SignInScreen()
            }
            composable<SignUpRoute> {
                SignUpScreen()
            }
            composable<ForgetPasswordRoute> {
                ForgetPasswordScreen()
            }
            composable<OnBoardingRoute> {
                OnBoardingScreen()
            }
            composable<MainRoute> {
                MainScreen()
            }
            composable <ProfileRoute>{
                ProfileScreen()
            }
            composable<EditProfileRoute> {
                EditProfileScreen()
            }
        }
    }

}
package com.dipuguide.shopsee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dipuguide.shopsee.presentation.navigation.ShopSeeNavGraph
import com.dipuguide.shopsee.presentation.screens.starter.forgetpassword.ForgetPasswordScreen
import com.dipuguide.shopsee.presentation.screens.starter.onboarding.OnBoardingScreen
import com.dipuguide.shopsee.presentation.screens.starter.signUp.SignUpScreen
import com.dipuguide.shopsee.presentation.ui.theme.ShopSeeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopSeeTheme(
                dynamicColor = false,
                darkTheme = false
            ) {
                ShopSeeNavGraph()
            }
        }
    }
}

package com.dipuguide.shopsee.presentation.screens.starter.splash

sealed class SplashAuthState {
    object Loading : SplashAuthState()
    object Authenticated : SplashAuthState()
    object Unauthenticated : SplashAuthState()
}
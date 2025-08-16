package com.dipuguide.shopsee.presentation.screens.starter.signIn

import com.dipuguide.shopsee.presentation.screens.starter.signUp.SignUpEvent

sealed class SignInEvent {
    data class OnEmailChange(val userEmail: String) : SignInEvent()
    data class OnPasswordChange(val password: String) : SignInEvent()
    object OnSignInClick : SignInEvent()
}
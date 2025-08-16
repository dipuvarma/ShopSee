package com.dipuguide.shopsee.presentation.screens.starter.signUp

sealed class SignUpEvent {
    data class OnNameChange(val userName: String) : SignUpEvent()
    data class OnEmailChange(val userEmail: String) : SignUpEvent()
    data class OnPasswordChange(val password: String) : SignUpEvent()
    object OnSignUpClick : SignUpEvent()
}
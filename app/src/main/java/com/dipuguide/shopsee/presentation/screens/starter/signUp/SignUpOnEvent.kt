package com.dipuguide.shopsee.presentation.screens.starter.signUp

sealed class SignUpOnEvent {
    data class OnNameChange(val userName: String) : SignUpOnEvent()
    data class OnEmailChange(val userEmail: String) : SignUpOnEvent()
    data class OnPasswordChange(val password: String) : SignUpOnEvent()
    object OnSignUpClick : SignUpOnEvent()
}
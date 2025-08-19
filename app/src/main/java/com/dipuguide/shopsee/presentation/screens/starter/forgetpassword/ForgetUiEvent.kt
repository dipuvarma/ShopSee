package com.dipuguide.shopsee.presentation.screens.starter.forgetpassword

sealed class ForgetUiEvent {

    data class EmailChange(val email: String) : ForgetUiEvent()
    object ForgetButtonClick : ForgetUiEvent()
}
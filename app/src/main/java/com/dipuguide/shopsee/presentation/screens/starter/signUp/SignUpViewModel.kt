package com.dipuguide.shopsee.presentation.screens.starter.signUp

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.shopsee.domain.model.UserDetail
import com.dipuguide.shopsee.domain.repo.AuthRepo
import com.dipuguide.shopsee.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _userDetailState = MutableStateFlow(SignUpUiState())
    val userDetailState: StateFlow<SignUpUiState> = _userDetailState.asStateFlow()


    fun onSignUpEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnSignUpClick -> {
                signUp(
                    name = userDetailState.value.name,
                    email = userDetailState.value.email,
                    password = userDetailState.value.password
                )
            }

            is SignUpEvent.OnEmailChange -> {
                _userDetailState.update {
                    it.copy(email = event.userEmail)
                }
            }

            is SignUpEvent.OnNameChange -> {
                _userDetailState.update {

                    it.copy(name = event.userName)
                }
            }

            is SignUpEvent.OnPasswordChange -> {
                _userDetailState.update {
                    it.copy(password = event.password)
                }
            }
        }
    }


    fun signUp(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _uiState.value = UiState.Error("All fields are required")
            return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepo.signUp(email = email, password = password)
            result.onSuccess {
                saveUserDetail(UserDetail(name = name, email = email))
                _uiState.value = UiState.Success("Account created successfully!")
            }
            result.onFailure { error ->
                _uiState.value =
                    UiState.Error(error.localizedMessage ?: "Sign up failed. Please try again.")
            }
        }
    }

    fun saveUserDetail(userDetail: UserDetail) {
        viewModelScope.launch {
            authRepo.saveUserDetail(userDetail).onSuccess {
                _uiState.value = UiState.Success("User Details saved")
            }.onFailure { e ->
                _uiState.value = UiState.Error(e.localizedMessage ?: "User Details save failed")
            }
        }
    }


    fun resetUiState() {
        _uiState.value = UiState.Idle
    }

}
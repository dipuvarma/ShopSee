package com.dipuguide.shopsee.presentation.screens.starter.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.shopsee.domain.model.UserDetail
import com.dipuguide.shopsee.domain.repo.AuthRepo
import com.dipuguide.shopsee.presentation.common.state.UiState
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _userDetailState = MutableStateFlow(UserDetail())
    val userDetailState: StateFlow<UserDetail> = _userDetailState.asStateFlow()

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated.asStateFlow()


    fun onSignUpEvent(event: SignUpOnEvent) {
        when (event) {
            is SignUpOnEvent.OnSignUpClick -> {
                signUp(
                    name = userDetailState.value.userName,
                    email = userDetailState.value.userEmail,
                    password = userDetailState.value.userPassword
                )

            }

            is SignUpOnEvent.OnEmailChange -> {
                _userDetailState.update {
                    it.copy(userEmail = event.userEmail)
                }
            }

            is SignUpOnEvent.OnNameChange -> {
                _userDetailState.update {
                    it.copy(userName = event.userName)
                }
            }

            is SignUpOnEvent.OnPasswordChange -> {
                _userDetailState.update {
                    it.copy(userPassword = event.password)
                }
            }
        }
    }


    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepo.signUp(email = email, password = password)
            result.onSuccess {
                _uiState.value = UiState.Success("Account created successfully!")
                authRepo.checkAuthStatus().collectLatest { isAuth ->
                    _isUserAuthenticated.value = isAuth
                }
            }
            result.onFailure { error ->
                _uiState.value =
                    UiState.Success(error.localizedMessage ?: "Sign up failed. Please try again.")
            }
        }
    }

    fun resetUiState() {
        _uiState.value = UiState.Idle
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepo.signIn(email = email, password = password)
            result.onSuccess {
                _uiState.value = UiState.Success("Sign in successfully!")
                authRepo.checkAuthStatus().collectLatest { isAuth ->
                    _isUserAuthenticated.value = isAuth
                }
            }
            result.onFailure { error ->
                _uiState.value =
                    UiState.Success(error.localizedMessage ?: "Sign in failed. Please try again.")
            }
        }
    }

}
package com.dipuguide.shopsee.presentation.screens.starter.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.shopsee.domain.model.UserDetail
import com.dipuguide.shopsee.domain.repo.AuthRepo
import com.dipuguide.shopsee.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _userDetailState = MutableStateFlow(UserDetail())
    val userDetailState: StateFlow<UserDetail> = _userDetailState.asStateFlow()

    private val _isUserAuthenticated = MutableStateFlow(false)
    val isUserAuthenticated: StateFlow<Boolean> = _isUserAuthenticated.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChange -> {
                _userDetailState.update {
                    it.copy(
                        userEmail = event.userEmail
                    )
                }
            }

            is SignInEvent.OnPasswordChange -> {
                _userDetailState.update {
                    it.copy(
                        userPassword = event.password
                    )
                }
            }

            is SignInEvent.OnSignInClick -> {
                signIn(
                    email = userDetailState.value.userEmail,
                    password = userDetailState.value.userPassword
                )
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepo.signIn(email = email, password = password)
            result.onSuccess {
                _uiState.value = UiState.Success("Sign in successfully!")
            }
            result.onFailure {
                _uiState.value = UiState.Error(it.localizedMessage ?: "Sign in failed!")
            }
        }
    }

    fun resetUiState(){
        _uiState.value = UiState.Idle
    }

}
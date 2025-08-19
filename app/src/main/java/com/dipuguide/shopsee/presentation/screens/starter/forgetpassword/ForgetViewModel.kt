package com.dipuguide.shopsee.presentation.screens.starter.forgetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.shopsee.domain.repo.AuthRepo
import com.dipuguide.shopsee.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _forgetUiState = MutableStateFlow(ForgetUiState())
    val forgetUiState: StateFlow<ForgetUiState> = _forgetUiState.asStateFlow()


    fun onEvent(event: ForgetUiEvent) {
        when (event) {
            is ForgetUiEvent.EmailChange -> {
                _forgetUiState.update {
                    it.copy(email = event.email)
                }
            }

            ForgetUiEvent.ForgetButtonClick -> {
                resetPassword(
                    email = forgetUiState.value.email
                )
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = authRepo.forgetPassword(email)

            result.onSuccess {
                _uiState.value = UiState.Success("Reset Link Send")
            }
            result.onFailure {
                _uiState.value = UiState.Error("Failed reset link send")
            }
        }
    }

    fun resetUiState(){
        _uiState.value = UiState.Idle
    }

}
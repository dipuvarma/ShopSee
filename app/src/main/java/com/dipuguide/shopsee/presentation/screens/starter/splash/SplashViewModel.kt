package com.dipuguide.shopsee.presentation.screens.starter.splash


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipuguide.shopsee.domain.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {


    private val _splashAuthState = MutableStateFlow<SplashAuthState>(SplashAuthState.Loading)
    val splashAuthState: StateFlow<SplashAuthState> = _splashAuthState.asStateFlow()

    init {
        checkUserAuthenticated()
    }

    fun checkUserAuthenticated() {
        viewModelScope.launch {
            authRepo.checkAuthStatus().collectLatest { isAuthenticated ->
                _splashAuthState.value = if (isAuthenticated) {
                    SplashAuthState.Authenticated
                } else {
                    SplashAuthState.Unauthenticated
                }
            }
        }
    }

}
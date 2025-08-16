package com.dipuguide.shopsee.domain.repo

import android.content.Intent
import androidx.compose.runtime.MutableState
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun forgetPassword(email: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun checkAuthStatus(): Flow<Boolean>
}
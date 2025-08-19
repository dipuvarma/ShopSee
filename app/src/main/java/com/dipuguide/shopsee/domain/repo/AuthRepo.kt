package com.dipuguide.shopsee.domain.repo

import com.dipuguide.shopsee.domain.model.UserDetail
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun forgetPassword(email: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
    fun checkAuthStatus(): Flow<Boolean>
    fun getCurrentUser(): Flow<FirebaseUser?>

    fun getUserDetail(): Flow<UserDetail>
    suspend fun saveUserDetail(userDetail: UserDetail): Result<Unit>
    suspend fun updateUserDetail(userDetail: UserDetail): Result<Unit>

}
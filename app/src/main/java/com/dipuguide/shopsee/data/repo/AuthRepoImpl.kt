package com.dipuguide.shopsee.data.repo

import android.util.Log
import com.dipuguide.shopsee.domain.model.UserDetail
import com.dipuguide.shopsee.domain.repo.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.util.Listener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import kotlin.math.log

class AuthRepoImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepo {

    companion object {
        const val USERS_COLLECTION = "users"
        private const val TAG = "AuthRepoImpl"
    }

    override suspend fun signUp(
        email: String,
        password: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            require(email.isNotBlank()) { "Email cannot be blank" }
            require(password.isNotBlank()) { "Password cannot be blank" }

            auth.createUserWithEmailAndPassword(email, password).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "signUp: Failed -> ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }

    override suspend fun signIn(
        email: String,
        password: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d(TAG, "signIn: Success for $email")
            Result.success(Unit)
        } catch (e: FirebaseAuthException) {
            Log.w(TAG, "signIn: FirebaseAuth error -> ${e.errorCode}", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "signIn: Failed -> ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }

    override suspend fun forgetPassword(email: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            require(email.isNotBlank()) { "Email cannot be blank" }
            auth.sendPasswordResetEmail(email).await()
            Log.d(TAG, "forgetPassword: Email sent to $email")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "forgetPassword: Failed -> ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            auth.signOut()
            Log.d(TAG, "signOut: Success")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "signOut: Failed -> ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val user =
                auth.currentUser ?: return@withContext Result.failure(Throwable("User Not Found"))
            val uid = user.uid

            firestore.collection(USERS_COLLECTION).document(uid).delete().await()
            user.delete().await()

            Log.d(TAG, "deleteAccount: Success for userId=$uid")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "deleteAccount: Failed -> ${e.localizedMessage}", e)
            Result.failure(e)
        }
    }

    override fun checkAuthStatus(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser != null).isSuccess
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun getCurrentUser(): Flow<FirebaseUser?> = flow {
        emit(auth.currentUser)
    }

    override fun getUserDetail(): Flow<UserDetail> = callbackFlow {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            close(IllegalStateException("User not logged in"))
            return@callbackFlow
        }

        val listener = firestore.collection(USERS_COLLECTION)
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(TAG, "getUserDetail: Firestore error -> ${error.localizedMessage}", error)
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val userDetail = snapshot.toObject(UserDetail::class.java)
                    if (userDetail != null) {
                        trySend(userDetail).isSuccess
                        Log.d(TAG, "getUserDetail: Updated -> $userDetail")
                    } else {
                        Log.w(TAG, "getUserDetail: Snapshot exists but is null")
                    }
                }
            }

        awaitClose { listener.remove() }
    }.catch { e ->
        Log.e(TAG, "getUserDetail: Flow crashed -> ${e.localizedMessage}", e)
        throw e
    }

    override suspend fun saveUserDetail(userDetail: UserDetail): Result<Unit> {
       return withContext(Dispatchers.IO){
            val userId = auth.currentUser?.uid
                ?: throw FirebaseAuthException("USER_NULL", "User ID is null")

           val userDetails = UserDetail(
               userId = userId
           )

            val user = mapOf(
                "userId" to userDetails.userId,
                "name" to userDetail.name,
                "email" to userDetail.email,
                "phoneNumber" to userDetails.phoneNumber,
                "profileImage" to userDetail.profileImage,
                "createAt" to System.currentTimeMillis(),
                "addresses" to userDetail.addresses
            )
            firestore.collection(USERS_COLLECTION).document(userId).set(user).await()
            Log.d(TAG, "saveUserDetail: Save User Details for userId=$userId")
            Result.success(Unit)
        }
    }

    override suspend fun updateUserDetail(userDetail: UserDetail): Result<Unit> =
        withContext(Dispatchers.IO) {
            val userId = auth.currentUser?.uid
                ?: return@withContext Result.failure(Throwable("User is not found"))
            try {
                firestore.collection(USERS_COLLECTION)
                    .document(userId)
                    .set(userDetail)
                    .await()
                Log.d(TAG, "updateUserDetail: Success for $userId")
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "updateUserDetail: Failed -> ${e.localizedMessage}", e)
                Result.failure(e)
            }
        }
}

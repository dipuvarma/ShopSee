package com.dipuguide.shopsee.presentation.common.component

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dipuguide.shopsee.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onSuccess: (FirebaseUser?) -> Unit,
    onError: (Exception?) -> Unit,
    title: String,
) {
    var isLoading by remember { mutableStateOf(false) }
    val onSignInResult = rememberLauncherForActivityResult(
        FirebaseAuthUIActivityResultContract()
    )
    { result ->
        isLoading = false
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                onSuccess(it)
            } ?: onError(Exception("Unable to get user"))
        } else {
            onError(response?.error)
        }
    }


    Button(
        onClick = {
            isLoading = true
            val provider = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            val signIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .build()
            onSignInResult.launch(signIntent)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary
        ),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.background,
                strokeWidth = 2.dp
            )
        } else {
            Image(
                painter = painterResource(R.drawable.google_icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = title)
        }
    }

}
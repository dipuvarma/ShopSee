package com.dipuguide.shopsee.presentation.screens.starter.forgetpassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dipuguide.shopsee.R
import com.dipuguide.shopsee.presentation.common.component.EmailTextField
import com.dipuguide.shopsee.presentation.common.state.UiState
import com.dipuguide.shopsee.presentation.navigation.LocalNavController
import com.dipuguide.shopsee.presentation.navigation.SignInRoute
import com.dipuguide.shopsee.presentation.screens.starter.signUp.SignUpScreen
import com.dipuguide.shopsee.presentation.ui.theme.ShopSeeTheme
import com.dipuguide.shopsee.utils.Dimen
import com.dipuguide.shopsee.utils.showToast

@Composable
fun ForgetPasswordScreen(modifier: Modifier = Modifier) {

    val navController = LocalNavController.current
    val context = LocalContext.current
    val viewModel = hiltViewModel<ForgetViewModel>()
    val forgetUiState by viewModel.forgetUiState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).message
                context.showToast(errorMessage)
                viewModel.resetUiState()
            }

            is UiState.Success -> {
                val successMessage = (uiState as UiState.Success).message
                context.showToast(successMessage)
                navController.navigate(SignInRoute) {
                    popUpTo(0)
                }
                viewModel.resetUiState()
            }

            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.PaddingMedium, vertical = Dimen.PaddingLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Forget Your Password",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "No worries - we'll help you reset it and get back to shop",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
        EmailTextField(
            value = forgetUiState.email,
            onValueChange = { email ->
                viewModel.onEvent(ForgetUiEvent.EmailChange(email))
            },
            label = stringResource(R.string.email),
            placeHolder = stringResource(R.string.enter_email)
        )
        Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {
                viewModel.onEvent(ForgetUiEvent.ForgetButtonClick)
            }
        ) {
            if (uiState is UiState.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Send Reset Link"
                )
            }

        }
        Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account?",
            )
            Spacer(modifier = Modifier.width(Dimen.SpacerSmall))
            Text(
                text = "Sign In",
                modifier = Modifier.clickable {
                    navController.navigate(SignInRoute) {
                        popUpTo(0)
                    }
                },
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun GetStartPreview() {
    ShopSeeTheme {
        ForgetPasswordScreen()
    }
}
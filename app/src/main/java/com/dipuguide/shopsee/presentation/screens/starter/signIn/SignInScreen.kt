package com.dipuguide.shopsee.presentation.screens.starter.signIn

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dipuguide.shopsee.R
import com.dipuguide.shopsee.presentation.common.component.EmailTextField
import com.dipuguide.shopsee.presentation.common.component.GoogleSignInButton
import com.dipuguide.shopsee.presentation.common.component.PasswordTextField
import com.dipuguide.shopsee.presentation.common.state.UiState
import com.dipuguide.shopsee.presentation.navigation.LocalNavController
import com.dipuguide.shopsee.presentation.navigation.SignUpRoute
import com.dipuguide.shopsee.presentation.ui.theme.ShopSeeTheme
import com.dipuguide.shopsee.utils.Dimen
import kotlinx.coroutines.launch

@Composable
fun SignInScreen() {

    val viewModel = hiltViewModel<SignInViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userDetails by viewModel.userDetailState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).message
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = errorMessage)
                }
                viewModel.resetUiState()
            }

            is UiState.Success -> {
                val successMessage = (uiState as UiState.Success).message
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(message = successMessage)
                }
                navController.navigate(SignUpRoute)
                viewModel.resetUiState()
            }

            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Dimen.PaddingMedium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                painter = painterResource(id = R.drawable.shop_see_logo_with_bg),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(Dimen.SpacerExtraLarge))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Log in to continue shopping.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(Dimen.SpacerSmall))
            EmailTextField(
                value = userDetails.userEmail,
                onValueChange = { email ->
                    viewModel.onEvent(SignInEvent.OnEmailChange(email))
                },
                label = stringResource(R.string.email),
                placeHolder = stringResource(R.string.enter_email)
            )
            Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
            PasswordTextField(
                value = userDetails.userPassword,
                onValueChange = { password ->
                    viewModel.onEvent(SignInEvent.OnPasswordChange(password))
                },
                label = stringResource(R.string.password),
                placeHolder = stringResource(R.string.enter_password)
            )
            Spacer(modifier = Modifier.height(Dimen.SpacerSmall))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Forget Password?",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End
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
                    viewModel.onEvent(SignInEvent.OnSignInClick)
                }
            ) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Sign In"
                    )
                }
            }
            Spacer(modifier = Modifier.height(Dimen.SpacerMedium))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = Dimen.PaddingSmall),
                    text = "Or Sign In With",
                )
                HorizontalDivider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
            GoogleSignInButton(
                onSuccess = {

                },
                onError = {

                },
                title = "Sign in with Google"
            )
            Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don’t have an account?",
                )
                Spacer(modifier = Modifier.width(Dimen.SpacerSmall))
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GetStartPreview() {
    ShopSeeTheme {
        SignInScreen()
    }
}
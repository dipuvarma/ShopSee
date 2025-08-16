package com.dipuguide.shopsee.presentation.screens.starter.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dipuguide.shopsee.presentation.screens.starter.signIn.SignInScreen
import com.dipuguide.shopsee.presentation.ui.theme.ShopSeeTheme
import com.dipuguide.shopsee.utils.Dimen

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimen.PaddingMedium, vertical = Dimen.PaddingLarge),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Start Your Shopping Journey Now",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
        Text(
            text = "Your one-stop shop for amazing deals & trendy products. Secure payments, trusted sellers, and quick delivery.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Dimen.SpacerMedium))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {

            }
        ) {
            Text(
                text = "Sign In"
            )
        }
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

            }
        ) {
            Text(
                text = "Sign Up"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GetStartPreview() {
    ShopSeeTheme {
        OnBoardingScreen()
    }
}
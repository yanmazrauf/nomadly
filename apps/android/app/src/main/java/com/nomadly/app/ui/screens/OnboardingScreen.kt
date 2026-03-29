package com.nomadly.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nomadly.app.ui.components.GhostButton
import com.nomadly.app.ui.components.PrimaryButton
import com.nomadly.app.ui.theme.AccentCyan
import com.nomadly.app.ui.theme.BrandTeal
import com.nomadly.app.ui.theme.ManropeFontFamily
import com.nomadly.app.ui.theme.NomadlyTheme
import com.nomadly.app.ui.theme.NotoSerifFontFamily
import com.nomadly.app.ui.theme.RustOrange
import com.nomadly.app.ui.theme.White

@Composable
fun OnboardingScreen(
    onStartExploring: () -> Unit,
    onSignIn: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        AsyncImage(
            model = "https://picsum.photos/seed/amalfi/800/1200",
            contentDescription = "Travel destination",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark gradient scrim overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.15f),
                            Color.Black.copy(alpha = 0.1f),
                            Color.Black.copy(alpha = 0.55f),
                            Color.Black.copy(alpha = 0.80f)
                        )
                    )
                )
        )

        // Decorative orange glow (bottom-right)
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd)
                .blur(120.dp)
                .background(
                    RustOrange.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(999.dp)
                )
        )

        // Decorative teal glow (top-left)
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopStart)
                .blur(80.dp)
                .background(
                    BrandTeal.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(999.dp)
                )
        )

        // Top logo
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 32.dp)
        ) {
            Text(
                text = "Nomadly",
                fontFamily = NotoSerifFontFamily,
                fontWeight = FontWeight.Black,
                fontSize = 30.sp,
                color = White,
                letterSpacing = (-1.5).sp
            )
        }

        // Bottom content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Hero headline
            val headline = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontFamily = NotoSerifFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        color = White,
                        letterSpacing = (-1.2).sp
                    )
                ) {
                    append("Your Next\n")
                }
                withStyle(
                    SpanStyle(
                        fontFamily = NotoSerifFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 48.sp,
                        color = AccentCyan,
                        letterSpacing = (-1.2).sp
                    )
                ) {
                    append("Escape")
                }
                withStyle(
                    SpanStyle(
                        fontFamily = NotoSerifFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 48.sp,
                        color = White,
                        letterSpacing = (-1.2).sp
                    )
                ) {
                    append(" Awaits")
                }
            }
            Text(text = headline, lineHeight = 52.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = "Discover curated journeys tailored to your soul.",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = White.copy(alpha = 0.90f),
                lineHeight = 27.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Onboarding dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Active dot — pill
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(AccentCyan)
                )
                // Inactive dots
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(White.copy(alpha = 0.30f))
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Primary CTA button
            PrimaryButton(
                text = "START EXPLORING  →",
                onClick = onStartExploring,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ghost / Sign In button
            GhostButton(
                text = "SIGN IN",
                onClick = onSignIn,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    NomadlyTheme {
        OnboardingScreen(
            onStartExploring = {},
            onSignIn = {}
        )
    }
}

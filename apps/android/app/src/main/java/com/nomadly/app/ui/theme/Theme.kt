package com.nomadly.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val NomadlyLightColorScheme = lightColorScheme(
    primary = BrandTeal,
    onPrimary = White,
    primaryContainer = AccentCyan,
    onPrimaryContainer = TealDark,
    secondary = TealAlt,
    onSecondary = White,
    secondaryContainer = CreamSurface,
    onSecondaryContainer = PrimaryText,
    tertiary = RustOrange,
    onTertiary = White,
    tertiaryContainer = RustLight,
    onTertiaryContainer = White,
    background = Cream,
    onBackground = PrimaryText,
    surface = CreamSurface,
    onSurface = PrimaryText,
    surfaceVariant = SurfaceAlt,
    onSurfaceVariant = SecondaryText,
    error = Destructive,
    onError = White,
    outline = SurfaceAlt,
    outlineVariant = CreamDark,
)

@Composable
fun NomadlyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = NomadlyLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Cream.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = NomadlyTypography,
        shapes = NomadlyShapes,
        content = content
    )
}

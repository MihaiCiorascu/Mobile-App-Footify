package com.example.footify.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = FootifyPurple,
    secondary = FootifyLightPurple,
    tertiary = FootifyGold,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = FootifyLightGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = FootifyPurple,
    onSurface = FootifyDarkGray,
    onSurfaceVariant = FootifyGray,
    primaryContainer = FootifyPurple,
    onPrimaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = FootifyPurple,
    secondary = FootifyLightPurple,
    tertiary = FootifyGold,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = FootifyLightGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = FootifyPurple,
    onSurface = FootifyDarkGray,
    onSurfaceVariant = FootifyGray,
    primaryContainer = FootifyPurple,
    onPrimaryContainer = Color.White
)

@Composable
fun FootifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
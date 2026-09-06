package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueVariant,
    onPrimaryContainer = PrimaryBlueContainer,
    secondary = PrimaryBlueContainer,
    onSecondary = OnPrimaryBlueContainer,
    tertiary = AccentCallText,
    onTertiary = Color.White,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = Color(0xFF2B2D33),
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    outline = BorderDark
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryBlueContainer,
    onPrimaryContainer = OnPrimaryBlueContainer,
    secondary = PrimaryBlue,
    onSecondary = Color.White,
    tertiary = AccentCallText,
    onTertiary = Color.White,
    background = CanvasBackground,
    surface = SurfaceLight,
    surfaceVariant = SurfaceSubtle,
    onBackground = TypographyPrimary,
    onSurface = TypographyPrimary,
    outline = BorderOutline,
    outlineVariant = BorderSubtle
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Use custom Bold Typography theme by default
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

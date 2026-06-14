package com.example.a211368_nelson_project2.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


@Composable
fun A211368_NELSON_PROJECT1Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // ❗ IMPORTANT (use your custom colors)
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            setUpEdgeToEdge(view, darkTheme)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // ✅ FIXED (no AppTypography error)
        content = content
    )
}

//////////////////////////////////////////////////
// 🌸 LIGHT THEME
//////////////////////////////////////////////////
private val LightColors = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,

    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,

    secondary = secondaryLight,
    onSecondary = onSecondaryLight,

    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,

    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,

    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,

    error = errorLight,
    onError = onErrorLight,

    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,

    background = backgroundLight,
    onBackground = onBackgroundLight,

    surface = surfaceLight,
    onSurface = onSurfaceLight,

    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,

    outline = outlineLight
)

private val PastelLightColorScheme = lightColorScheme(
    primary = Color(0xFF9FA8FF),          // pastel purple
    secondary = Color(0xFF9AD0FF),        // pastel blue
    tertiary = Color(0xFFFFC1E3),         // pastel pink

    background = Color(0xFFF8F9FF),       // soft white-blue
    surface = Color(0xFFFFFFFF),

    primaryContainer = Color(0xFFE6E6FF),
    secondaryContainer = Color(0xFFE0F2FF),
    tertiaryContainer = Color(0xFFFFEEF6),

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

//////////////////////////////////////////////////
// 🌙 DARK THEME
//////////////////////////////////////////////////
private val DarkColors = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,

    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,

    secondary = secondaryDark,
    onSecondary = onSecondaryDark,

    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,

    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,

    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,

    error = errorDark,
    onError = onErrorDark,

    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,

    background = backgroundDark,
    onBackground = onBackgroundDark,

    surface = surfaceDark,
    onSurface = onSurfaceDark,

    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,

    outline = outlineDark
)

//////////////////////////////////////////////////
// 🌟 EDGE TO EDGE (STATUS BAR)
//////////////////////////////////////////////////
private fun setUpEdgeToEdge(view: View, darkTheme: Boolean) {

    val window = (view.context as Activity).window

    WindowCompat.setDecorFitsSystemWindows(window, false)

    // Transparent status bar
    window.statusBarColor = Color.Transparent.toArgb()

    val navigationBarColor = when {
        Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
        Build.VERSION.SDK_INT >= 26 -> Color(0x63FFFFFF).toArgb()
        else -> Color(0x50000000).toArgb()
    }

    window.navigationBarColor = navigationBarColor

    val controller = WindowCompat.getInsetsController(window, view)

    controller.isAppearanceLightStatusBars = !darkTheme
    controller.isAppearanceLightNavigationBars = !darkTheme
}
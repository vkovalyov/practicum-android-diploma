package ru.practicum.android.diploma.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = Black,
    onBackground = White,
    surface = Gray,
    onSurface = Black,
    onSurfaceVariant = White
)

private val LightColorScheme = lightColorScheme(
    background = White,
    onBackground = Black,
    surface = LightGray,
    onSurface = Black,
    onSurfaceVariant = Gray

)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

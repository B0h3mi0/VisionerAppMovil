package com.example.appmov.ui.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(

)

private val LightColorScheme = lightColorScheme(


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
@RequiresApi(Build.VERSION_CODES.O)
fun customTypography(fontSize: Float): Typography {
    return Typography().copy(
        bodyLarge = TextStyle(fontSize = fontSize.sp),
        bodyMedium = TextStyle(fontSize = (fontSize - 2).sp),
        bodySmall = TextStyle(fontSize = (fontSize - 4).sp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppMovTheme(
    fontSize: Float = 35f, // valor por defecto
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = customTypography(fontSize),
        content = content
    )
}
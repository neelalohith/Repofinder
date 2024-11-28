package com.example.repofinder.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun GitHubSearchTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(),  // Use Material3's color scheme
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
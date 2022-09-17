package dev.logickoder.exhibits.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import dev.logickoder.exhibits.core.theme.ExhibitsTheme
import dev.logickoder.exhibits.presentation.exhibits.ExhibitsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExhibitsTheme {
                ExhibitsScreen(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
                )
            }
        }
    }
}
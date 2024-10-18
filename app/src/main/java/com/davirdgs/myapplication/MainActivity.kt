package com.davirdgs.myapplication

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.davirdgs.myapplication.ui.Navigation
import com.davirdgs.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setWindowContent { Main() }
    }

    private fun setWindowContent(
        parent: CompositionContext? = null,
        content: @Composable () -> Unit
    ) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.navigationBarColor = ContextCompat.getColor(
            this.applicationContext,
            android.R.color.transparent
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
        WindowInsetsControllerCompat(window, window.decorView).run {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = true
        }
        setContent(parent, content)
    }
}

@Composable
fun Main() {
    MyApplicationTheme {
        Navigation()
    }
}
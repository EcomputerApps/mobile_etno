package com.example.mobile_etno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobile_etno.components.TopBarHome
import com.example.mobile_etno.screens.ScreenHome
import com.example.mobile_etno.ui.theme.MobileetnoTheme
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuViewModel: MenuViewModel = MenuViewModel()

        setContent {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Colors.backgroundEtno)) {
                ScreenHome(viewModel = menuViewModel)
            }
        }
    }
}


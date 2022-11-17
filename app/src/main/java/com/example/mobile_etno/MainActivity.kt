package com.example.mobile_etno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.mobile_etno.utils.colors.Colors
import com.example.mobile_etno.viewmodels.MenuViewModel
import com.example.mobile_etno.views.ScreenHome

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menuViewModel: MenuViewModel = MenuViewModel()

        setContent {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Colors.backgroundEtno)) {
                ScreenHome(viewModel = menuViewModel, applicationContext)
            }
        }
    }
}


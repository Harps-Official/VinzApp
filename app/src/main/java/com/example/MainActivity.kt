package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MelofyViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
  private val viewModel: MelofyViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FirebaseApp.initializeApp(this)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val userProfile by viewModel.userProfile.collectAsState()
        if (userProfile == null) {
            AuthScreen(onAuthSuccess = { /* Automatically handled by AuthStateListener in ViewModel */ })
        } else {
            MainScreen(viewModel)
        }
      }
    }
  }
}

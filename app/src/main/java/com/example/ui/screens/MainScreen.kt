package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MelofyBackgrounds
import com.example.viewmodel.MelofyViewModel
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Search : Screen("search", "Cari", Icons.Filled.Search)
    object Collection : Screen("collection", "Koleksi", Icons.Filled.Favorite)
    object Premium : Screen("premium", "Premium", Icons.Filled.Star)
    object Account : Screen("account", "Akun", Icons.Filled.Person)
}

val items = listOf(Screen.Home, Screen.Search, Screen.Collection, Screen.Premium, Screen.Account)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MelofyViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showAiSheet by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAiSheet = true }) {
                Icon(Icons.Filled.AutoAwesome, contentDescription = "AI Assistant")
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MelofyBackgrounds.getBackgroundForTime())
                .padding(innerPadding)
        ) {
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { DashboardScreen() }
                composable(Screen.Search.route) { SearchScreen(viewModel) }
                composable(Screen.Collection.route) { CollectionScreen(viewModel) }
                composable(Screen.Premium.route) { PremiumScreen(viewModel) }
                composable(Screen.Account.route) { AccountScreen(viewModel) }
            }
        }
        
        if (showAiSheet) {
            ModalBottomSheet(onDismissRequest = { showAiSheet = false }) {
                AiAssistantSheet(viewModel)
            }
        }
    }
}

@Composable
fun AiAssistantSheet(viewModel: MelofyViewModel) {
    var query by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("Tanya rekomendasi musik kepadaku (Gemini 3.1 Flash-Lite)!") }
    val scope = rememberCoroutineScope()
    
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp).padding(bottom = 32.dp)) {
        Text("Melofy AI Assistant", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(response, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Pesan") },
            trailingIcon = {
                IconButton(onClick = {
                    val currentQuery = query
                    query = ""
                    response = "Berpikir..."
                    scope.launch {
                        response = viewModel.askGemini(currentQuery)
                    }
                }) {
                    Icon(Icons.Filled.Send, contentDescription = "Send")
                }
            }
        )
    }
}

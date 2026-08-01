package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MelofyViewModel
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(viewModel: MelofyViewModel) {
    val userProfile by viewModel.userProfile.collectAsState()
    var redeemCode by remember { mutableStateOf("") }
    var redeemResult by remember { mutableStateOf<String?>(null) }
    
    var pingYt by remember { mutableStateOf<Long?>(null) }
    var pingIg by remember { mutableStateOf<Long?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Akun", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                val name = userProfile?.name ?: "Guest"
                val email = userProfile?.email ?: "Not logged in"
                val plan = userProfile?.plan?.uppercase() ?: "FREE"
                Text("Nama: $name")
                Text("Email: $email")
                Text("Plan: $plan")
                if (userProfile?.activeRedeem != null) {
                    Text("Active Redeem: ${userProfile?.activeRedeem}")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Tukar Kode Redeem", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = redeemCode,
            onValueChange = { redeemCode = it },
            label = { Text("Kode Redeem") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                viewModel.redeemCode(redeemCode) { res ->
                    redeemResult = res
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text("Klaim Kode")
        }
        if (redeemResult != null) {
            Text("Result: \$redeemResult", color = MaterialTheme.colorScheme.primary)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Uji Latency API", style = MaterialTheme.typography.titleMedium)
        
        Button(
            onClick = {
                scope.launch {
                    pingYt = viewModel.ping("https://api.neoxr.eu/api/youtube?url=https://www.youtube.com/watch?v=fKRtnMYMW08&type=video&quality=1080p&apikey=VinzKeyNeoxr0021")
                    pingIg = viewModel.ping("https://api.neoxr.eu/api/ig?url=https://www.instagram.com/p/CK0tLXyAzEI&apikey=VinzKeyNeoxr0021")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("JALANKAN UJI LATENCY")
        }
        
        if (pingYt != null) {
            Text("YouTube API: \${pingYt}ms")
        }
        if (pingIg != null) {
            Text("Instagram API: \${pingIg}ms")
        }
    }
}

package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MelofyViewModel

@Composable
fun PremiumScreen(viewModel: MelofyViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Melofy Premium", style = MaterialTheme.typography.headlineMedium)
        Text("Dengarkan musik tanpa batas, kualitas ultra-HD 320 Kbps & offline mode.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Paket Pelajar", style = MaterialTheme.typography.titleLarge)
                Text("Rp 18.000 / bulan", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Text("Limit harian 200 unduhan. Khusus siswa & mahasiswa aktif.", style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Paket Pro", style = MaterialTheme.typography.titleLarge)
                Text("Rp 25.000 / bulan", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Text("Limit harian 500 unduhan. Akses penuh ke semua fitur & audio kualitas tinggi.", style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Flagship Annual", style = MaterialTheme.typography.titleLarge)
                Text("Rp 380.000 / tahun", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
                Text("Unlimited Limit Harian! Akses VIP 1 Tahun penuh tanpa batas kompresi.", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

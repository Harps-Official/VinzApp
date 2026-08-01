package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.MusicVideo
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Melofy", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.primary)
        Text("Platform Tersedia", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(16.dp))
        
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.VideoLibrary, contentDescription = "YouTube", tint = androidx.compose.ui.graphics.Color.Red)
                Spacer(modifier = Modifier.width(16.dp))
                Text("YouTube Downloader")
            }
        }
        
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CameraAlt, contentDescription = "Instagram", tint = androidx.compose.ui.graphics.Color(0xFFE1306C))
                Spacer(modifier = Modifier.width(16.dp))
                Text("Instagram Downloader")
            }
        }
        
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.MusicVideo, contentDescription = "TikTok", tint = androidx.compose.ui.graphics.Color.Cyan)
                Spacer(modifier = Modifier.width(16.dp))
                Text("TikTok Downloader")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("Baru Diputar", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Belum ada riwayat putar.", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

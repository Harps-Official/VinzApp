package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MelofyViewModel

@Composable
fun CollectionScreen(viewModel: MelofyViewModel) {
    val offlineSongs by viewModel.offlineSongs.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Koleksi Tersimpan", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (offlineSongs.isEmpty()) {
            Text("Koleksi masih kosong.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        } else {
            LazyColumn {
                items(offlineSongs) { song ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(song.title, style = MaterialTheme.typography.titleMedium)
                            Text(song.artist, style = MaterialTheme.typography.bodyMedium)
                            Text("Tersedia Offline", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

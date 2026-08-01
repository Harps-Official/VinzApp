package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.model.SongData
import com.example.viewmodel.MelofyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: MelofyViewModel) {
    var query by remember { mutableStateOf("") }
    val results by viewModel.searchResults.collectAsState()
    val loading by viewModel.searchLoading.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Cari Musik", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Judul lagu / Artis...") },
            trailingIcon = {
                IconButton(onClick = { viewModel.search(query) }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(results ?: emptyList()) { song ->
                    SongItem(song, onToggleOffline = { isOffline ->
                        // In a real app we would get the stream URL first then save
                    })
                }
            }
        }
    }
}

@Composable
fun SongItem(song: SongData, onToggleOffline: (Boolean) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { }) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = song.thumbnail ?: song.image,
                contentDescription = "Cover",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(song.title ?: "Unknown Title", style = MaterialTheme.typography.titleMedium)
                Text(song.artist ?: song.author ?: "Unknown Artist", style = MaterialTheme.typography.bodyMedium)
                if (song.duration != null) {
                    Text(song.duration, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

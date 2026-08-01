package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offline_songs")
data class OfflineSong(
    @PrimaryKey val id: String,
    val title: String,
    val artist: String,
    val coverUrl: String,
    val sourceUrl: String,
    val localFilePath: String
)

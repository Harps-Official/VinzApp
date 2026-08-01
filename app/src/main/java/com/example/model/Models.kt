package com.example.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NeoxrResponse(
    val status: Boolean,
    val data: List<SongData>? = null,
    val result: BotcahxResult? = null
)

@JsonClass(generateAdapter = true)
data class BotcahxResult(
    val data: AudioData? = null
)

@JsonClass(generateAdapter = true)
data class AudioData(
    val url: String? = null
)

@JsonClass(generateAdapter = true)
data class SongData(
    val title: String? = null,
    val artist: String? = null,
    val author: String? = null,
    val thumbnail: String? = null,
    val image: String? = null,
    val url: String? = null,
    val duration: String? = null
)

data class Song(
    val id: String = "",
    val title: String = "",
    val artist: String = "",
    val coverUrl: String = "",
    val sourceUrl: String = "",
    val duration: String = "",
    val isOffline: Boolean = false
)

data class UserProfile(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val nickname: String = "",
    val photoUrl: String = "",
    val plan: String = "free",
    val limit: Int = 10,
    val isFlagship: Boolean = false,
    val activeRedeem: String? = null,
    val redeemExpiry: Long? = null
)

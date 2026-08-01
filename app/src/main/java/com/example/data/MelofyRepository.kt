package com.example.data

import android.content.Context
import com.example.api.RetrofitClient
import com.example.model.NeoxrResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

class MelofyRepository(private val context: Context) {
    private val api = RetrofitClient.api
    private val db = AppDatabase.getDatabase(context).offlineSongDao()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    
    suspend fun searchSongs(query: String): Result<NeoxrResponse> {
        return try {
            val res = api.searchSpotify(query)
            Result.success(res)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getStreamUrl(url: String): Result<String> {
        return try {
            val res = api.downloadSpotify(url)
            val streamUrl = res.result?.data?.url ?: throw Exception("URL not found")
            Result.success(streamUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    fun getOfflineSongs() = db.getAllOfflineSongs()
    
    suspend fun downloadAndSaveSong(id: String, title: String, artist: String, coverUrl: String, streamUrl: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(streamUrl)
                val connection = url.openConnection()
                connection.connect()
                
                val input = connection.getInputStream()
                val file = File(context.filesDir, "offline_\${id}.mp3")
                val output = FileOutputStream(file)
                
                input.copyTo(output)
                output.close()
                input.close()
                
                db.insert(OfflineSong(id, title, artist, coverUrl, streamUrl, file.absolutePath))
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
    
    suspend fun deleteOfflineSong(id: String) {
        withContext(Dispatchers.IO) {
            val song = db.getSongById(id)
            if (song != null) {
                val file = File(song.localFilePath)
                if (file.exists()) {
                    file.delete()
                }
                db.deleteById(id)
            }
        }
    }
    
    // Redeem Code Logic
    suspend fun redeemCode(code: String): Result<String> {
        val user = auth.currentUser ?: return Result.failure(Exception("Not logged in"))
        val codeUpper = code.uppercase()
        if (codeUpper == "MELOFYPELAJAR") {
            try {
                val userRef = firestore.collection("users").document(user.uid)
                val doc = userRef.get().await()
                if (doc.exists() && doc.getString("activeRedeem") == "MELOFYPELAJAR") {
                    return Result.failure(Exception("Code already redeemed!"))
                }
                // 7 days from now
                val expiry = System.currentTimeMillis() + (7L * 24 * 60 * 60 * 1000)
                userRef.set(mapOf(
                    "plan" to "pelajar",
                    "limit" to 200,
                    "activeRedeem" to codeUpper,
                    "redeemExpiry" to expiry
                ), com.google.firebase.firestore.SetOptions.merge()).await()
                return Result.success("Pelajar Plan activated for 7 days!")
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
        return Result.failure(Exception("Invalid Code"))
    }
    
    // Latency Ping
    suspend fun pingService(url: String): Long {
        return withContext(Dispatchers.IO) {
            val start = System.currentTimeMillis()
            try {
                api.pingService(url)
                System.currentTimeMillis() - start
            } catch (e: Exception) {
                -1L
            }
        }
    }

    // Gemini API
    suspend fun askGemini(prompt: String): String {
        return try {
            val request = mapOf(
                "contents" to listOf(
                    mapOf(
                        "parts" to listOf(
                            mapOf("text" to prompt)
                        )
                    )
                )
            )
            val key = com.example.BuildConfig.GEMINI_API_KEY.ifEmpty { "PLACEHOLDER_KEY" }
            val res = api.generateContent(key, request)
            if (res.isSuccessful) {
                val body = res.body() as? Map<*, *>
                val candidates = body?.get("candidates") as? List<*>
                val firstCandidate = candidates?.firstOrNull() as? Map<*, *>
                val content = firstCandidate?.get("content") as? Map<*, *>
                val parts = content?.get("parts") as? List<*>
                val firstPart = parts?.firstOrNull() as? Map<*, *>
                firstPart?.get("text") as? String ?: "No response text found."
            } else {
                "Error: \${res.code()}"
            }
        } catch (e: Exception) {
            "API Exception: \${e.message}"
        }
    }
}

package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MelofyRepository
import com.example.data.OfflineSong
import com.example.model.SongData
import com.example.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MelofyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MelofyRepository(application)
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SongData>?>(null)
    val searchResults: StateFlow<List<SongData>?> = _searchResults.asStateFlow()
    
    private val _searchLoading = MutableStateFlow(false)
    val searchLoading: StateFlow<Boolean> = _searchLoading.asStateFlow()

    val offlineSongs = repository.getOfflineSongs()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                viewModelScope.launch {
                    try {
                        val doc = firestore.collection("users").document(user.uid).get().await()
                        if (doc.exists()) {
                            val profile = doc.toObject(UserProfile::class.java)
                            _userProfile.value = profile
                        } else {
                            val newProfile = UserProfile(
                                uid = user.uid,
                                email = user.email ?: "",
                                name = user.displayName ?: "User",
                                nickname = user.displayName ?: "User",
                                photoUrl = user.photoUrl?.toString() ?: ""
                            )
                            firestore.collection("users").document(user.uid).set(newProfile).await()
                            _userProfile.value = newProfile
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                _userProfile.value = null
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _searchLoading.value = true
            val result = repository.searchSongs(query)
            if (result.isSuccess) {
                _searchResults.value = result.getOrNull()?.data
            } else {
                _searchResults.value = emptyList()
            }
            _searchLoading.value = false
        }
    }
    
    suspend fun getStreamUrl(url: String): String? {
        return repository.getStreamUrl(url).getOrNull()
    }
    
    fun toggleOffline(songData: SongData, isCurrentlyOffline: Boolean, streamUrl: String?) {
        viewModelScope.launch {
            val id = songData.url ?: return@launch
            if (isCurrentlyOffline) {
                repository.deleteOfflineSong(id)
            } else {
                if (streamUrl != null) {
                    repository.downloadAndSaveSong(
                        id,
                        songData.title ?: "Unknown",
                        songData.artist ?: "Unknown",
                        songData.thumbnail ?: "",
                        streamUrl
                    )
                }
            }
        }
    }
    
    fun redeemCode(code: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val result = repository.redeemCode(code)
            onResult(result.getOrNull() ?: result.exceptionOrNull()?.message ?: "Error")
            // Refresh profile
            auth.currentUser?.uid?.let { uid ->
                val doc = firestore.collection("users").document(uid).get().await()
                if (doc.exists()) {
                    _userProfile.value = doc.toObject(UserProfile::class.java)
                }
            }
        }
    }
    
    suspend fun ping(url: String): Long {
        return repository.pingService(url)
    }
    
    suspend fun askGemini(prompt: String): String {
        return repository.askGemini(prompt)
    }
    
    fun logout() {
        auth.signOut()
    }
}

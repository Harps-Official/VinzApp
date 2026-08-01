package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflineSongDao {
    @Query("SELECT * FROM offline_songs")
    fun getAllOfflineSongs(): Flow<List<OfflineSong>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: OfflineSong)
    
    @Query("DELETE FROM offline_songs WHERE id = :id")
    suspend fun deleteById(id: String)
    
    @Query("SELECT * FROM offline_songs WHERE id = :id LIMIT 1")
    suspend fun getSongById(id: String): OfflineSong?
}

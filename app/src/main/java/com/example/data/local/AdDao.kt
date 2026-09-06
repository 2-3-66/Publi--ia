package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AdDao {
    @Query("SELECT * FROM saved_ads ORDER BY createdAt DESC")
    fun getAllAds(): Flow<List<AdEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAd(ad: AdEntity): Long

    @Query("DELETE FROM saved_ads WHERE id = :id")
    suspend fun deleteAdById(id: Long)

    @Query("SELECT * FROM saved_ads WHERE id = :id LIMIT 1")
    suspend fun getAdById(id: Long): AdEntity?
}

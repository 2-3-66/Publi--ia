package com.example.data.repository

import com.example.data.generator.AdGenerator
import com.example.data.local.AdDao
import com.example.data.local.AdEntity
import com.example.data.model.AdRequest
import com.example.data.model.GeneratedAd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AdRepository(
    private val adDao: AdDao,
    private val adGenerator: AdGenerator = AdGenerator()
) {

    val savedAds: Flow<List<GeneratedAd>> = adDao.getAllAds().map { entities ->
        entities.map { it.toGeneratedAd() }
    }

    suspend fun generateAd(request: AdRequest): GeneratedAd {
        val generated = adGenerator.generateAd(request)
        val insertedId = adDao.insertAd(AdEntity.fromGeneratedAd(generated))
        return generated.copy(id = insertedId)
    }

    suspend fun deleteAd(id: Long) {
        adDao.deleteAdById(id)
    }
}

package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.GeneratedAd

@Entity(tableName = "saved_ads")
data class AdEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val businessName: String,
    val activityType: String,
    val whatsappAd: String,
    val facebookAd: String,
    val tiktokText: String,
    val slogan: String,
    val videoScript: String,
    val city: String,
    val address: String,
    val landmark: String,
    val whatsappNumber: String,
    val phoneNumber: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toGeneratedAd(): GeneratedAd {
        return GeneratedAd(
            id = id,
            businessName = businessName,
            activityType = activityType,
            whatsappAd = whatsappAd,
            facebookAd = facebookAd,
            tiktokText = tiktokText,
            slogan = slogan,
            videoScript = videoScript,
            city = city,
            address = address,
            landmark = landmark,
            whatsappNumber = whatsappNumber,
            phoneNumber = phoneNumber,
            createdAt = createdAt
        )
    }

    companion object {
        fun fromGeneratedAd(ad: GeneratedAd): AdEntity {
            return AdEntity(
                id = ad.id,
                businessName = ad.businessName,
                activityType = ad.activityType,
                whatsappAd = ad.whatsappAd,
                facebookAd = ad.facebookAd,
                tiktokText = ad.tiktokText,
                slogan = ad.slogan,
                videoScript = ad.videoScript,
                city = ad.city,
                address = ad.address,
                landmark = ad.landmark,
                whatsappNumber = ad.whatsappNumber,
                phoneNumber = ad.phoneNumber,
                createdAt = ad.createdAt
            )
        }
    }
}

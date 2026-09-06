package com.example.data.model

data class AdRequest(
    val businessName: String = "",
    val activityType: String = "Maquis & Restaurant",
    val tone: String = "Populaire & Dynamique",
    val productOrService: String = "",
    val description: String = "",
    val city: String = "Abidjan",
    val address: String = "",
    val landmark: String = "",
    val price: String = "",
    val dateTime: String = "",
    val whatsappNumber: String = "",
    val phoneNumber: String = "",
    val specialOffer: String = "",
    val mapLink: String = ""
) {
    val isValid: Boolean
        get() = businessName.trim().isNotBlank() && productOrService.trim().isNotBlank()
}

data class GeneratedAd(
    val id: Long = 0,
    val businessName: String,
    val activityType: String,
    val whatsappAd: String,
    val facebookAd: String,
    val tiktokText: String,
    val slogan: String,
    val videoScript: String,
    val city: String = "",
    val address: String = "",
    val landmark: String = "",
    val whatsappNumber: String = "",
    val phoneNumber: String = "",
    val price: String = "",
    val specialOffer: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

data class BusinessPreset(
    val name: String,
    val activityType: String,
    val defaultProduct: String,
    val defaultDescription: String,
    val defaultPrice: String,
    val defaultOffer: String,
    val iconName: String
)

enum class FlyerTheme(
    val label: String,
    val subtitle: String,
    val primaryColorHex: Long,
    val secondaryColorHex: Long,
    val accentColorHex: Long,
    val textColorHex: Long
) {
    GOLD_LUXURY(
        "Or & Prestige",
        "Fond d'ébène et dorures étincelantes",
        0xFF121214,
        0xFF22201D,
        0xFFFFD700,
        0xFFFFFFFF
    ),
    TROPICAL_WARM(
        "Soleil & Énergie",
        "Ambiance festive, braise et chaleur",
        0xFFD9480F,
        0xFFE8590C,
        0xFFFFD43B,
        0xFFFFFFFF
    ),
    COBALT_PRO(
        "Bleu Cobalt Pro",
        "Design moderne, épuré et corporate",
        0xFF0A2540,
        0xFF003D73,
        0xFF00D4B2,
        0xFFFFFFFF
    ),
    NEON_NIGHT(
        "Néon Événementiel",
        "Violet vibrant et reflets électro",
        0xFF1A0A2A,
        0xFF3B1261,
        0xFFFF007F,
        0xFFFFFFFF
    ),
    EMERALD_FRESH(
        "Émeraude Nature",
        "Fraîcheur, confiance et bien-être",
        0xFF064E3B,
        0xFF047857,
        0xFF6EE7B7,
        0xFFFFFFFF
    )
}

enum class FlyerFormat(val label: String, val subLabel: String, val aspectRatio: Float) {
    SQUARE("Carré 1:1", "Post WhatsApp & Instagram", 1.0f),
    STORY("Story 9:16", "Statut WhatsApp & TikTok", 0.65f),
    BANNER("Bannière 16:9", "Couverture Facebook", 1.6f)
}

data class FlyerConfig(
    val headlineTag: String = "GRAND ARRIVAGE",
    val theme: FlyerTheme = FlyerTheme.TROPICAL_WARM,
    val format: FlyerFormat = FlyerFormat.SQUARE,
    val customHighlight: String = "",
    val showQrOrContactBadge: Boolean = true
)

data class ClientMessage(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val messageText: String
)

data class BudgetSimulation(
    val budgetFcfa: Int,
    val minReach: Int,
    val maxReach: Int,
    val minClicks: Int,
    val maxClicks: Int,
    val estimatedCostPerLeadFcfa: Int,
    val bestPostingTimes: String,
    val recommendedAudience: String,
    val strategicTips: List<String>
)

package com.example.data.generator

import android.util.Log
import com.example.BuildConfig
import com.example.data.model.AdRequest
import com.example.data.model.GeneratedAd
import com.example.data.remote.GeminiClient
import com.example.data.remote.GeminiContent
import com.example.data.remote.GeminiPart
import com.example.data.remote.GeminiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdGenerator {

    suspend fun generateAd(request: AdRequest): GeneratedAd = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val prompt = buildGeminiPrompt(request)
                val geminiRequest = GeminiRequest(
                    contents = listOf(
                        GeminiContent(
                            parts = listOf(GeminiPart(text = prompt))
                        )
                    )
                )
                val response = GeminiClient.service.generateContent(apiKey, geminiRequest)
                val responseText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                if (!responseText.isNullOrBlank()) {
                    val parsed = parseGeminiResponse(responseText, request)
                    if (parsed != null) {
                        return@withContext parsed
                    }
                }
            } catch (e: Exception) {
                Log.e("AdGenerator", "Gemini API call failed, falling back to local engine: ${e.message}", e)
            }
        }

        // Fallback or immediate local engine
        generateLocalAd(request)
    }

    private fun buildGeminiPrompt(req: AdRequest): String {
        return """
Tu es un expert publicitaire et copywriter professionnel spécialisé pour les commerces, entreprises, restaurants, maquis, artisans et organisateurs d'événements en Afrique francophone.
Rédige une campagne publicitaire captivante, chaleureuse, persuasive et adaptée aux réalités locales pour :

- Nom : ${req.businessName}
- Secteur / Type d'activité : ${req.activityType}
- Style & Tonalité souhaitée : ${req.tone}
- Produit / Service / Événement : ${req.productOrService}
- Description & Ambiance : ${req.description.ifBlank { "Qualité supérieure et service soigné" }}
- Ville : ${req.city}
- Lieu / Adresse : ${req.address.ifBlank { req.city }}
- Point de repère : ${req.landmark.ifBlank { "Facile d'accès" }}
- Prix : ${if (req.price.isNotBlank()) req.price else "Tarifs très abordables"}
- Date & Heure : ${if (req.dateTime.isNotBlank()) req.dateTime else "Ouvert en continu"}
- WhatsApp : ${req.whatsappNumber}
- Téléphone : ${req.phoneNumber}
- Offre spéciale / Promo : ${if (req.specialOffer.isNotBlank()) req.specialOffer else "Accueil VIP"}

IMPORTANT: Tu dois formater STRICTEMENT ta réponse avec ces balises exactes :

[WHATSAPP]
(Publicité courte spéciale pour statuts WhatsApp et diffusion en groupes avec emojis attrayants, appel à l'action clair et coordonnées complètes)
[/WHATSAPP]

[FACEBOOK]
(Texte publicitaire percutant pour Facebook avec titre accrocheur, mise en valeur des avantages, offre, localisation précise et hashtags)
[/FACEBOOK]

[TIKTOK]
(Texte court dynamique pour vidéo TikTok : Accroche percutante dans les 3 premières secondes, texte rythmé et hashtags tendance)
[/TIKTOK]

[SLOGAN]
(Un slogan unique, court, mémorisable et percutant)
[/SLOGAN]

[SCRIPT_VIDEO]
(Court script vidéo de 30-45 secondes avec plans visuels indiqués [Plan 1], [Plan 2] et texte de la voix-off dynamique)
[/SCRIPT_VIDEO]
        """.trimIndent()
    }

    private fun parseGeminiResponse(rawText: String, req: AdRequest): GeneratedAd? {
        val whatsapp = extractTag(rawText, "WHATSAPP")
        val facebook = extractTag(rawText, "FACEBOOK")
        val tiktok = extractTag(rawText, "TIKTOK")
        val slogan = extractTag(rawText, "SLOGAN")
        val videoScript = extractTag(rawText, "SCRIPT_VIDEO")

        if (whatsapp == null || facebook == null) return null

        return GeneratedAd(
            businessName = req.businessName,
            activityType = req.activityType,
            whatsappAd = whatsapp.trim(),
            facebookAd = facebook.trim(),
            tiktokText = (tiktok ?: "Découvrez ${req.productOrService} chez ${req.businessName} ! #Afrique #Qualite").trim(),
            slogan = (slogan ?: "${req.businessName} : L'excellence au rendez-vous !").trim(),
            videoScript = (videoScript ?: "[Plan 1 - Accueil]\nVoix-off: Bienvenue chez ${req.businessName} !").trim(),
            city = req.city,
            address = req.address,
            landmark = req.landmark,
            whatsappNumber = req.whatsappNumber,
            phoneNumber = req.phoneNumber,
            price = req.price,
            specialOffer = req.specialOffer
        )
    }

    private fun extractTag(content: String, tag: String): String? {
        val startTag = "[$tag]"
        val endTag = "[/$tag]"
        val startIndex = content.indexOf(startTag)
        val endIndex = content.indexOf(endTag)
        return if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            content.substring(startIndex + startTag.length, endIndex).trim()
        } else null
    }

    fun generateLocalAd(req: AdRequest): GeneratedAd {
        val name = req.businessName.ifBlank { "Notre Établissement" }
        val product = req.productOrService.ifBlank { "Nos prestations" }
        val city = req.city.ifBlank { "notre ville" }
        val locationStr = buildString {
            if (req.address.isNotBlank()) append(req.address)
            if (req.landmark.isNotBlank()) {
                if (isNotEmpty()) append(" - ")
                append("Repère: ${req.landmark}")
            }
            if (req.city.isNotBlank()) {
                if (isNotEmpty()) append(", ")
                append(req.city)
            }
        }.ifBlank { "Contactez-nous pour l'adresse exacte" }

        val priceStr = if (req.price.isNotBlank()) req.price else "Prix imbattable en FCFA"
        val promoStr = if (req.specialOffer.isNotBlank()) "\n🎁 OFFRE SPÉCIALE : ${req.specialOffer} !" else ""
        val dateStr = if (req.dateTime.isNotBlank()) "\n📅 QUAND : ${req.dateTime}" else ""
        val contactStr = buildString {
            if (req.whatsappNumber.isNotBlank()) append("\n📲 WhatsApp : ${req.whatsappNumber}")
            if (req.phoneNumber.isNotBlank()) append("\n📞 Infoline / Appel : ${req.phoneNumber}")
        }

        val whatsappAd = """
🔥 ALERTE BON PLAN À $city ! 🔥

Envie du meilleur pour $product ?
Venez découvrir *${name.uppercase()}* ! ✨

👉 $product
💰 Tarif : $priceStr$promoStr$dateStr
📍 Localisation : $locationStr
$contactStr

⚡ Les places et stocks sont limités ! Écrivez-nous vite sur WhatsApp pour réserver ou commander dès maintenant ! 🚀
        """.trimIndent()

        val facebookAd = """
📣 OFFICIEL À $city ! L'adresse incontournable : ${name.uppercase()} ! 🌟

Vous cherchez la qualité, le professionnalisme et la convivialité pour votre $product ?
Ne cherchez plus ailleurs, *${name}* est là pour vous régaler et vous satisfaire !

${if (req.description.isNotBlank()) "✨ " + req.description else "✨ Une expérience unique conçue pour répondre à toutes vos attentes avec un savoir-faire d'exception."}

💎 NOS ATOUTS :
✔️ Prestations & produits de premier choix : $product
✔️ Prix étudiés pour tous : $priceStr$promoStr$dateStr

📍 ADRESSE : $locationStr

📞 CONTACTEZ-NOUS DÈS AUJOURD'HUI :$contactStr

Partagez la publication et taguez vos proches qui ont besoin de ce bon plan ! 👇🔥
#${city.replace(" ", "")} #BonPlan #Qualite #Promotion #Afrique
        """.trimIndent()

        val tiktokAd = """
POV : Tu cherches le meilleur endroit pour $product à $city... 😱🔥

Arrête de scroller ! 🛑 C'est chez ${name} que ça se passe ! 
Des saveurs, du style et une ambiance de folie. 

Tarif choc : $priceStr !
${if (req.specialOffer.isNotBlank()) "Et en plus : ${req.specialOffer} 🎁" else "Service garanti 100% satisfaction !"}

Infos & Réservations en bio ou DM 📲 $contactStr

#tiktok${city.lowercase()} #bonplan #pourtoi #foryou #viral #servicequalite
        """.trimIndent()

        val slogan = when {
            req.activityType.contains("Maquis", ignoreCase = true) || req.activityType.contains("Resto", ignoreCase = true) ->
                "${name} : Le goût authentique qui rassemble !"
            req.activityType.contains("Salon", ignoreCase = true) || req.activityType.contains("Beauté", ignoreCase = true) ->
                "${name} : Sublimez votre éclat chaque jour."
            req.activityType.contains("Boutique", ignoreCase = true) ->
                "${name} : Votre style, notre passion au meilleur prix !"
            req.activityType.contains("Concert", ignoreCase = true) || req.activityType.contains("Événement", ignoreCase = true) ->
                "${name} : Vivez l'événement le plus vibrant de l'année !"
            else ->
                "${name} : Votre satisfaction, notre signature."
        }

        val videoScript = """
⏱️ DURÉE : 30 Secondes
🎬 SCRIPT PUBLICITAIRE : "${name}"

[00:00 - 00:05] PLAN 1 - ACCROCHE
• Visuel : Gros plan dynamique sur $product dans une lumière chaleureuse.
• Voix-off (chaleureuse & rythmée) : "Vous cherchez le meilleur pour $product à $city ?"

[00:05 - 00:15] PLAN 2 - LA SOLUTION
• Visuel : Plans rapides montrant l'équipe souriante, les détails du service et l'ambiance chez $name.
• Voix-off : "Bienvenue chez $name ! Ici, la qualité rencontre la passion pour vous offrir une expérience mémorable."

[00:15 - 00:23] PLAN 3 - L'OFFRE CHOC
• Visuel : Texte incrusté en grand avec le prix "$priceStr"${if (req.specialOffer.isNotBlank()) " et le badge cadeau \"${req.specialOffer}\"" else ""}.
• Voix-off : "Profitez de nos offres exceptionnelles dès aujourd'hui !"

[00:23 - 00:30] PLAN 4 - APPEL À L'ACTION
• Visuel : Logo $name, adresse "$locationStr" et boutons WhatsApp/Téléphone.
• Voix-off : "Ne tardez pas ! Contactez-nous sur WhatsApp ou appelez directement. $name, l'adresse de référence !"
        """.trimIndent()

        return GeneratedAd(
            businessName = name,
            activityType = req.activityType,
            whatsappAd = whatsappAd,
            facebookAd = facebookAd,
            tiktokText = tiktokAd,
            slogan = slogan,
            videoScript = videoScript,
            city = req.city,
            address = req.address,
            landmark = req.landmark,
            whatsappNumber = req.whatsappNumber,
            phoneNumber = req.phoneNumber,
            price = req.price,
            specialOffer = req.specialOffer
        )
    }
}

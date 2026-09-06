package com.example.data.generator

import com.example.data.model.BudgetSimulation
import com.example.data.model.ClientMessage
import com.example.data.model.FlyerConfig
import com.example.data.model.GeneratedAd

object MarketingServices {

    fun generateClientMessages(ad: GeneratedAd): List<ClientMessage> {
        val name = ad.businessName.ifBlank { "Notre Établissement" }
        val product = if (ad.slogan.isNotBlank()) ad.slogan else "nos prestations de qualité"
        val city = ad.city.ifBlank { "votre secteur" }
        val priceStr = if (ad.price.isNotBlank()) ad.price else "nos tarifs préférentiels"
        val offerStr = if (ad.specialOffer.isNotBlank()) ad.specialOffer else "notre accueil VIP"

        return listOf(
            ClientMessage(
                id = "welcome",
                title = "Accueil & Présentation Directe",
                category = "AUTOMATIQUE WHATSAPP",
                description = "Envoyez ce message dès qu'un prospect vous écrit pour la première fois sur WhatsApp.",
                messageText = """
Bonjour et chaleureuse bienvenue chez *$name* ! 😊✨

Nous sommes ravis de vous accueillir.
Vous recherchez des informations sur nos prestations, nos disponibilités ou notre menu ?

👉 *Notre produit phare :* $product
📍 *Localisation :* ${ad.address.ifBlank { city }} (${ad.landmark.ifBlank { "Facile d'accès" }})
💰 *Tarif :* $priceStr
🎁 *Offre actuelle :* $offerStr

Dites-nous ce qui vous ferait plaisir, nous vous répondons dans un instant ! ⚡
                """.trimIndent()
            ),
            ClientMessage(
                id = "followup",
                title = "Relance Promo Flash (Anti-Hésitation)",
                category = "RELANCE PROSPECT",
                description = "À envoyer aux clients qui n'ont pas finalisé leur commande ou réservation après 24h.",
                messageText = """
Coucou ! C'est l'équipe de *$name* à $city 👋

Nous avons remarqué votre intérêt pour nos services ($product). 
Les places et disponibilités partent très vite aujourd'hui ! ⏳

🔥 *Rappel spécial pour vous :*
$offerStr pour toute confirmation aujourd'hui !

Souhaitez-vous que nous vous réservions votre place dès maintenant ? Écrivez-nous juste "OUI" en retour ! 📲
                """.trimIndent()
            ),
            ClientMessage(
                id = "order_confirm",
                title = "Confirmation de Commande & Livraison",
                category = "COMMANDE & PAIEMENT",
                description = "Message professionnel récapitulatif pour rassurer le client sur sa commande.",
                messageText = """
🎉 *COMMANDE VALIDÉE CHEZ $name !*

Merci infiniment pour votre confiance ! Voici le récapitulatif :
📦 *Article / Prestation :* $product
💰 *Total :* $priceStr
📍 *Lieu de prise en charge / Destination :* ${ad.address.ifBlank { city }}
📞 *Contact direct :* ${ad.phoneNumber.ifBlank { ad.whatsappNumber }}

Notre équipe prépare votre demande avec le plus grand soin. Un livreur / gestionnaire vous contactera très rapidement ! À tout de suite ✨
                """.trimIndent()
            ),
            ClientMessage(
                id = "loyalty",
                title = "Fidélité & Parrainage Cadeau",
                category = "SATISFACTION CLIENT",
                description = "À envoyer 2 à 3 jours après la prestation pour fidéliser et inciter à revenir.",
                messageText = """
Bonjour cher(e) client(e) de *$name* ! 🌟

Toute l'équipe espère que vous avez pleinement apprécié votre expérience avec nous.
Votre satisfaction est notre plus grande fierté !

🎁 *CADEAU VIP FIDÉLITÉ :*
Lors de votre prochain passage, ou en venant accompagné d'un proche, montrez ce message pour bénéficier d'un avantage exclusif : $offerStr !

Partagez notre contact à vos proches : ${ad.whatsappNumber} ! Belle journée à vous 💖
                """.trimIndent()
            )
        )
    }

    fun simulateBudget(budgetFcfa: Int, activityType: String, city: String): BudgetSimulation {
        val safeBudget = budgetFcfa.coerceAtLeast(1000)

        // 1 000 FCFA generates approx ~ 1,800 to 3,600 impressions/reach
        val minReach = (safeBudget * 1.8).toInt()
        val maxReach = (safeBudget * 3.6).toInt()

        // Click-through rate approx 1.2% - 2.5%
        val minClicks = (minReach * 0.012).toInt().coerceAtLeast(10)
        val maxClicks = (maxReach * 0.024).toInt().coerceAtLeast(minClicks + 15)

        val costPerLead = (safeBudget / ((minClicks + maxClicks) / 2.0)).toInt().coerceAtLeast(20)

        val timing = when {
            activityType.contains("Maquis", true) || activityType.contains("Resto", true) || activityType.contains("Concert", true) ->
                "Du jeudi au dimanche entre 11h30-14h (déjeuner) et 18h-23h (soirées d'ambiance)"
            activityType.contains("Salon", true) || activityType.contains("Beauté", true) || activityType.contains("Boutique", true) ->
                "Du mercredi au samedi, et surtout en fin de mois (du 27 au 6 du mois suivant)"
            else ->
                "Tous les jours entre 12h-14h et 18h-21h (pics de connexion WhatsApp & Facebook)"
        }

        val audience = when {
            activityType.contains("Maquis", true) || activityType.contains("Resto", true) ->
                "Hommes & Femmes 20-50 ans, habitant à $city dans un rayon de 7 km, centres d'intérêt : Gastronomie, Sorties, Musique africaine"
            activityType.contains("Salon", true) || activityType.contains("Beauté", true) ->
                "Femmes 18-45 ans, $city, centres d'intérêt : Coiffure, Mode féminine, Cosmétique, Bien-être"
            activityType.contains("Boutique", true) ->
                "Hommes & Femmes 20-45 ans, $city, centres d'intérêt : Prêt-à-porter, Shopping en ligne, Chaussures"
            activityType.contains("Immobilier", true) ->
                "Cadres, professionnels & diaspora 28-60 ans, centres d'intérêt : Investissement, Voyage, Hôtellerie"
            else ->
                "Public actif 22-55 ans résidant à $city, utilisation quotidienne de smartphones et réseaux sociaux"
        }

        val tips = listOf(
            "Ciblez un rayon géographique précis (ex: 5 à 10 km autour de votre boutique ou maquis) plutôt que tout le pays.",
            "Ajoutez un bouton 'Envoyer un message WhatsApp' directement sur votre publicité sponsorisée.",
            "Répondez aux messages clients en moins de 10 minutes pour multiplier vos ventes par 3.",
            "Utilisez des visuels clairs avec le prix et l'adresse écrits en gros caractères pour attirer l'œil instantanément."
        )

        return BudgetSimulation(
            budgetFcfa = safeBudget,
            minReach = minReach,
            maxReach = maxReach,
            minClicks = minClicks,
            maxClicks = maxClicks,
            estimatedCostPerLeadFcfa = costPerLead,
            bestPostingTimes = timing,
            recommendedAudience = audience,
            strategicTips = tips
        )
    }

    fun generateFlyerCaption(ad: GeneratedAd, config: FlyerConfig): String {
        return """
📢 *${config.headlineTag} CHEZ ${ad.businessName.uppercase()} !* 🌟

${ad.slogan}

✨ *DÉTAILS :*
👉 ${ad.whatsappAd.lines().take(4).joinToString("\n")}

📍 *Retrouvez-nous :* ${ad.address.ifBlank { ad.city }}
📲 *WhatsApp :* ${ad.whatsappNumber}
📞 *Infoline :* ${ad.phoneNumber}

#${ad.city.replace(" ", "")} #Flyer #${config.headlineTag.replace(" ", "")} #BonPlan
        """.trimIndent()
    }
}

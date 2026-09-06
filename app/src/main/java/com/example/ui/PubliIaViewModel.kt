package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.AdRequest
import com.example.data.model.BusinessPreset
import com.example.data.model.FlyerConfig
import com.example.data.model.FlyerFormat
import com.example.data.model.FlyerTheme
import com.example.data.model.GeneratedAd
import com.example.data.repository.AdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ScreenTab {
    CREATE,
    RESULT,
    STUDIO,
    SERVICES,
    HISTORY
}

sealed interface GenerationState {
    data object Idle : GenerationState
    data object Loading : GenerationState
    data class Success(val ad: GeneratedAd) : GenerationState
    data class Error(val message: String) : GenerationState
}

class PubliIaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AdRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AdRepository(database.adDao())
    }

    val savedAds: StateFlow<List<GeneratedAd>> = repository.savedAds
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentTab = MutableStateFlow(ScreenTab.CREATE)
    val currentTab: StateFlow<ScreenTab> = _currentTab.asStateFlow()

    private val _formState = MutableStateFlow(AdRequest())
    val formState: StateFlow<AdRequest> = _formState.asStateFlow()

    private val _generationState = MutableStateFlow<GenerationState>(GenerationState.Idle)
    val generationState: StateFlow<GenerationState> = _generationState.asStateFlow()

    private val _flyerConfig = MutableStateFlow(FlyerConfig())
    val flyerConfig: StateFlow<FlyerConfig> = _flyerConfig.asStateFlow()

    private val _budgetFcfa = MutableStateFlow(5000)
    val budgetFcfa: StateFlow<Int> = _budgetFcfa.asStateFlow()

    val presets = listOf(
        BusinessPreset(
            name = "Chez Tantie Alice",
            activityType = "Maquis & Restaurant",
            defaultProduct = "Poulet Braisé & Alloco chaud",
            defaultDescription = "Ambiance chaleureuse ivoirienne, assaisonnements secrets du terroir, terrasse ventilée.",
            defaultPrice = "3 500 FCFA",
            defaultOffer = "1 boisson fraîche offerte pour 2 plats commandés",
            iconName = "restaurant"
        ),
        BusinessPreset(
            name = "Salon Glamour Beauté",
            activityType = "Salon de coiffure & Beauté",
            defaultProduct = "Tresses africaines & Soins capillaires",
            defaultDescription = "Spécialiste nattes collées, tissages et lissage. Cadre climatisé et équipe soignée.",
            defaultPrice = "8 000 FCFA",
            defaultOffer = "-20% sur la pose perruque ce samedi",
            iconName = "spa"
        ),
        BusinessPreset(
            name = "Boutique Mode Élite",
            activityType = "Boutique & Vêtements",
            defaultProduct = "Robes de soirée & Costumes italiens",
            defaultDescription = "Nouvel arrivage de prêt-à-porter chic pour femmes et hommes tendance.",
            defaultPrice = "15 000 FCFA",
            defaultOffer = "Livraison offerte partout en ville dès 2 articles",
            iconName = "shopping_bag"
        ),
        BusinessPreset(
            name = "Délices Pâtisserie & Gâteaux",
            activityType = "Pâtisserie & Événement",
            defaultProduct = "Gâteaux d'anniversaire personnalisés & Petits fours",
            defaultDescription = "Saveurs exquises chocolat, vanille et fruits frais. Décoration sur mesure.",
            defaultPrice = "12 000 FCFA",
            defaultOffer = "Bougies étincelantes et carte de vœux offertes",
            iconName = "cake"
        ),
        BusinessPreset(
            name = "Galaxy High-Tech & Phone",
            activityType = "Vente Téléphones & High-Tech",
            defaultProduct = "Smartphones dernière génération & Accessoires",
            defaultDescription = "Appareils neufs et scellés avec garantie 12 mois. Écouteurs et chargeurs rapides.",
            defaultPrice = "65 000 FCFA",
            defaultOffer = "Pochette antichoc + blindé offerts à l'achat",
            iconName = "smartphone"
        ),
        BusinessPreset(
            name = "Concert Festival Ambiance",
            activityType = "Concert & Événement",
            defaultProduct = "Grand Concert Live des Vacances",
            defaultDescription = "Les plus grands artistes en live, son et lumière spectaculaire, sécurité totale garantie.",
            defaultPrice = "5 000 FCFA (VIP 15 000 FCFA)",
            defaultOffer = "Conso gratuite pour les 100 premiers arrivés",
            iconName = "event"
        ),
        BusinessPreset(
            name = "Pressing Blanchisserie Express",
            activityType = "Pressing & Blanchisserie",
            defaultProduct = "Nettoyage à sec & Repassage express 24h",
            defaultDescription = "Entretien soigné de vos vestes, draps, rideaux et tenues traditionnelles.",
            defaultPrice = "2 000 FCFA",
            defaultOffer = "Ramassage et livraison à domicile gratuits dès 5 pièces",
            iconName = "local_laundry_service"
        ),
        BusinessPreset(
            name = "Garage Mécanique Express",
            activityType = "Artisan & Garage",
            defaultProduct = "Diagnostic & Recharge Climatisation Auto",
            defaultDescription = "Mécaniciens qualifiés, matériel électronique de pointe, rapidité et garantie.",
            defaultPrice = "10 000 FCFA",
            defaultOffer = "Diagnostic électronique offert avec la recharge clim",
            iconName = "build"
        ),
        BusinessPreset(
            name = "Agence Horizon Immo",
            activityType = "Agence Immobilière",
            defaultProduct = "Appartements meublés & Villas de standing",
            defaultDescription = "Logements sécurisés avec Wi-Fi haut débit, piscine, groupe électrogène et gardiennage 24h/24.",
            defaultPrice = "30 000 FCFA / jour",
            defaultOffer = "Tarif dégressif dès 5 nuitées consécutives",
            iconName = "home"
        )
    )

    fun setTab(tab: ScreenTab) {
        _currentTab.value = tab
    }

    fun updateBusinessName(value: String) {
        _formState.value = _formState.value.copy(businessName = value)
    }

    fun updateActivityType(value: String) {
        _formState.value = _formState.value.copy(activityType = value)
    }

    fun updateTone(value: String) {
        _formState.value = _formState.value.copy(tone = value)
    }

    fun updateProductOrService(value: String) {
        _formState.value = _formState.value.copy(productOrService = value)
    }

    fun updateDescription(value: String) {
        _formState.value = _formState.value.copy(description = value)
    }

    fun updateCity(value: String) {
        _formState.value = _formState.value.copy(city = value)
    }

    fun updateAddress(value: String) {
        _formState.value = _formState.value.copy(address = value)
    }

    fun updateLandmark(value: String) {
        _formState.value = _formState.value.copy(landmark = value)
    }

    fun updatePrice(value: String) {
        _formState.value = _formState.value.copy(price = value)
    }

    fun updateDateTime(value: String) {
        _formState.value = _formState.value.copy(dateTime = value)
    }

    fun updateWhatsappNumber(value: String) {
        _formState.value = _formState.value.copy(whatsappNumber = value)
    }

    fun updatePhoneNumber(value: String) {
        _formState.value = _formState.value.copy(phoneNumber = value)
    }

    fun updateSpecialOffer(value: String) {
        _formState.value = _formState.value.copy(specialOffer = value)
    }

    fun updateFlyerTheme(theme: FlyerTheme) {
        _flyerConfig.value = _flyerConfig.value.copy(theme = theme)
    }

    fun updateFlyerFormat(format: FlyerFormat) {
        _flyerConfig.value = _flyerConfig.value.copy(format = format)
    }

    fun updateFlyerHeadlineTag(tag: String) {
        _flyerConfig.value = _flyerConfig.value.copy(headlineTag = tag)
    }

    fun updateBudgetFcfa(amount: Int) {
        _budgetFcfa.value = amount
    }

    fun applyPreset(preset: BusinessPreset) {
        _formState.value = _formState.value.copy(
            businessName = preset.name,
            activityType = preset.activityType,
            productOrService = preset.defaultProduct,
            description = preset.defaultDescription,
            price = preset.defaultPrice,
            specialOffer = preset.defaultOffer,
            city = if (_formState.value.city.isBlank()) "Abidjan" else _formState.value.city,
            address = if (_formState.value.address.isBlank()) "Cocody Angré 8ème tranche" else _formState.value.address,
            landmark = if (_formState.value.landmark.isBlank()) "À 50m de la Pharmacie des Arcades" else _formState.value.landmark,
            whatsappNumber = if (_formState.value.whatsappNumber.isBlank()) "+225 07 00 11 22 33" else _formState.value.whatsappNumber,
            phoneNumber = if (_formState.value.phoneNumber.isBlank()) "+225 05 44 55 66 77" else _formState.value.phoneNumber
        )
    }

    fun generateAd() {
        val currentForm = _formState.value
        if (!currentForm.isValid) {
            _generationState.value = GenerationState.Error("Veuillez renseigner au moins le nom et le produit/service.")
            return
        }

        viewModelScope.launch {
            _generationState.value = GenerationState.Loading
            try {
                val ad = repository.generateAd(currentForm)
                _generationState.value = GenerationState.Success(ad)
                _currentTab.value = ScreenTab.RESULT
            } catch (e: Exception) {
                _generationState.value = GenerationState.Error(e.message ?: "Erreur lors de la génération")
            }
        }
    }

    fun viewSavedAd(ad: GeneratedAd) {
        _generationState.value = GenerationState.Success(ad)
        _currentTab.value = ScreenTab.RESULT
    }

    fun deleteSavedAd(id: Long) {
        viewModelScope.launch {
            repository.deleteAd(id)
        }
    }

    fun clearForm() {
        _formState.value = AdRequest()
    }
}

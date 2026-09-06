package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.GenerationState
import com.example.ui.PubliIaViewModel
import com.example.ui.components.SectionContainer
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.NavyHeaderDark
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.TypographyMuted
import com.example.ui.theme.TypographyPrimary
import com.example.ui.theme.TypographySecondary
import com.example.ui.theme.VibrantOrange
import androidx.compose.foundation.BorderStroke

@Composable
fun CreateScreen(
    viewModel: PubliIaViewModel,
    modifier: Modifier = Modifier
) {
    val form by viewModel.formState.collectAsState()
    val genState by viewModel.generationState.collectAsState()

    val activityCategories = listOf(
        "Maquis & Restaurant",
        "Salon de coiffure & Beauté",
        "Boutique & Vêtements",
        "Pâtisserie & Événement",
        "Vente Téléphones & High-Tech",
        "Concert & Événement",
        "Pressing & Blanchisserie",
        "Artisan & Garage",
        "Agence Immobilière",
        "Commerce général",
        "Autre"
    )

    val tones = listOf(
        "Populaire & Dynamique",
        "Prestige & Luxe",
        "Promo Flash & Urgence",
        "Professionnel & Sérieux"
    )

    val quickCities = listOf("Abidjan", "Dakar", "Douala", "Yaoundé", "Lomé", "Cotonou", "Ouagadougou", "Bamako")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // --- Quick Preset Tester Card ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = NavyHeaderDark
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF60A5FA),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "EXEMPLES RAPIDES (1 CLIC)",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        )
                    }

                    TextButton(onClick = { viewModel.clearForm() }) {
                        Text(
                            text = "Effacer",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.presets.forEach { preset ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { viewModel.applyPreset(preset) }
                                .testTag("preset_${preset.name.replace(" ", "_").lowercase()}")
                        ) {
                            Text(
                                text = preset.name,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // --- Section 1: Informations Principales ---
        SectionContainer(
            title = "1. Votre Établissement ou Événement",
            icon = Icons.Default.Business
        ) {
            OutlinedTextField(
                value = form.businessName,
                onValueChange = { viewModel.updateBusinessName(it) },
                label = { Text("Nom de l'entreprise ou événement *") },
                placeholder = { Text("ex: Chez Tantie Alice / Mega Concert Vacances") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("business_name_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Type d'activité :",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                activityCategories.forEach { category ->
                    val isSelected = form.activityType == category
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.updateActivityType(category) },
                        label = { Text(category, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.Transparent,
                            labelColor = TypographySecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) PrimaryBlue else BorderSubtle,
                            selectedBorderColor = PrimaryBlue,
                            borderWidth = 1.dp
                        ),
                        modifier = Modifier.testTag("chip_${category.replace(" ", "_").lowercase()}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Tonalité & Ambiance de l'annonce :",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                tones.forEach { toneOption ->
                    val isSelected = form.tone == toneOption
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.updateTone(toneOption) },
                        label = { Text(toneOption, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.Transparent,
                            labelColor = TypographySecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) PrimaryBlue else BorderSubtle,
                            selectedBorderColor = PrimaryBlue,
                            borderWidth = 1.dp
                        ),
                        modifier = Modifier.testTag("tone_chip_${toneOption.replace(" ", "_").lowercase()}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = form.productOrService,
                onValueChange = { viewModel.updateProductOrService(it) },
                label = { Text("Produit, service ou événement à promouvoir *") },
                placeholder = { Text("ex: Poulet Braisé & Alloco / Tresses VIP / Pass Concert") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("product_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = form.description,
                onValueChange = { viewModel.updateDescription(it) },
                label = { Text("Description & Ambiance (détails importants)") },
                placeholder = { Text("ex: Cadre climatisé, saveurs authentiques, service rapide et convivial...") },
                minLines = 2,
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("description_input"),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // --- Section 2: Localisation & Accès ---
        SectionContainer(
            title = "2. Localisation & Accès",
            icon = Icons.Default.LocationOn
        ) {
            Text(
                text = "Ville :",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                quickCities.forEach { city ->
                    val isSelected = form.city == city
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.updateCity(city) },
                        label = { Text(city, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = form.city,
                onValueChange = { viewModel.updateCity(it) },
                label = { Text("Ville personnalisée") },
                placeholder = { Text("ex: Abidjan, Douala, Dakar, etc.") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("city_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = form.address,
                onValueChange = { viewModel.updateAddress(it) },
                label = { Text("Adresse ou Lieu précis") },
                placeholder = { Text("ex: Cocody Angré 8ème tranche / Akwa Bd Liberté") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("address_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = form.landmark,
                onValueChange = { viewModel.updateLandmark(it) },
                label = { Text("Point de repère (pour faciliter l'accès)") },
                placeholder = { Text("ex: Face à la pharmacie / À 100m du grand carrefour") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("landmark_input"),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // --- Section 3: Prix (FCFA) & Calendrier ---
        SectionContainer(
            title = "3. Tarifs & Date (si nécessaire)",
            icon = Icons.Default.Payments
        ) {
            OutlinedTextField(
                value = form.price,
                onValueChange = { viewModel.updatePrice(it) },
                label = { Text("Prix (exemples en FCFA)") },
                placeholder = { Text("ex: 3 500 FCFA / À partir de 10 000 FCFA / Entrée Libre") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("price_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = form.dateTime,
                onValueChange = { viewModel.updateDateTime(it) },
                label = { Text("Date & Heure (si applicable)") },
                placeholder = { Text("ex: Tous les jours de 11h à 23h / Ce samedi dès 19h") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("datetime_input"),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // --- Section 4: Contacts & Offre Spéciale ---
        SectionContainer(
            title = "4. Contacts & Offre Spéciale",
            icon = Icons.Default.Call
        ) {
            OutlinedTextField(
                value = form.whatsappNumber,
                onValueChange = { viewModel.updateWhatsappNumber(it) },
                label = { Text("Numéro WhatsApp") },
                placeholder = { Text("ex: +225 07 12 34 56 78") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("whatsapp_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    focusedLabelColor = PrimaryBlue
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = form.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = { Text("Numéro de téléphone direct (Appel)") },
                placeholder = { Text("ex: +225 05 98 76 54 32") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("phone_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = form.specialOffer,
                onValueChange = { viewModel.updateSpecialOffer(it) },
                label = { Text("Promotion ou offre spéciale") },
                placeholder = { Text("ex: 1 boisson offerte / -20% ce weekend / 50 premières places") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("promo_input"),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // --- Error message if any ---
        AnimatedVisibility(visible = genState is GenerationState.Error) {
            val errMsg = (genState as? GenerationState.Error)?.message ?: ""
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = errMsg,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }

        // --- Big CTA Button ---
        Button(
            onClick = { viewModel.generateAd() },
            enabled = form.isValid && genState !is GenerationState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .testTag("create_ad_button"),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VibrantOrange,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            if (genState is GenerationState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.5.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "GÉNÉRATION IA EN COURS...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            } else {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "CRÉER MA PUBLICITÉ",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

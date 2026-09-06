package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.generator.MarketingServices
import com.example.data.model.BudgetSimulation
import com.example.data.model.ClientMessage
import com.example.data.model.GeneratedAd
import com.example.ui.GenerationState
import com.example.ui.PubliIaViewModel
import com.example.ui.ScreenTab
import com.example.ui.theme.AccentWhatsAppBg
import com.example.ui.theme.AccentWhatsAppText
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.NavyHeaderDark
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.TypographyMuted
import com.example.ui.theme.TypographyPrimary
import com.example.ui.theme.TypographySecondary
import com.example.ui.theme.VibrantOrange

@Composable
fun ServicesScreen(
    viewModel: PubliIaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val genState by viewModel.generationState.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val budgetFcfa by viewModel.budgetFcfa.collectAsState()

    val currentAd: GeneratedAd = when (val s = genState) {
        is GenerationState.Success -> s.ad
        else -> GeneratedAd(
            businessName = formState.businessName.ifBlank { "CHEZ TANTIE ALICE" },
            activityType = formState.activityType,
            whatsappAd = "Spécialité Poulet Braisé & Alloco chaud savoureux.",
            facebookAd = "Venez déguster le meilleur poulet braisé !",
            tiktokText = "Le meilleur poulet braisé d'Abidjan !",
            slogan = "Le goût authentique qui rassemble !",
            videoScript = "Vidéo promotionnelle",
            city = formState.city.ifBlank { "Abidjan" },
            address = formState.address.ifBlank { "Cocody Angré 8ème tranche" },
            landmark = formState.landmark.ifBlank { "Près de la Pharmacie des Arcades" },
            whatsappNumber = formState.whatsappNumber.ifBlank { "+225 07 00 11 22 33" },
            phoneNumber = formState.phoneNumber.ifBlank { "+225 05 44 55 66 77" },
            price = formState.price.ifBlank { "3 500 FCFA" },
            specialOffer = formState.specialOffer.ifBlank { "1 boisson fraîche offerte" }
        )
    }

    val clientMessages = MarketingServices.generateClientMessages(currentAd)
    val simulation: BudgetSimulation = MarketingServices.simulateBudget(
        budgetFcfa = budgetFcfa,
        activityType = currentAd.activityType,
        city = currentAd.city
    )

    val budgetPresets = listOf(1000, 2500, 5000, 10000, 25000, 50000)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // --- Header Badge ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = NavyHeaderDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF2563EB)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "SERVICES MARKETING & ROI",
                        color = Color(0xFF93C5FD),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = "Convertissez Vos Prospects en Clients",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.4).sp
                )

                Text(
                    text = "Accédez aux scripts de réponse WhatsApp pré-rédigés pour votre activité et estimez la rentabilité de vos budgets de sponsorisation.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        // ==========================================
        // SECTION 1: SIMULATEUR DE BUDGET PUBLICITAIRE
        // ==========================================
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SIMULATEUR DE BUDGET & PORTÉE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    color = TypographyPrimary
                )
            }

            Text(
                text = "Sélectionnez votre budget de sponsorisation (Facebook, Instagram, WhatsApp Ads) :",
                fontSize = 12.sp,
                color = TypographySecondary
            )

            // Budget presets
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                budgetPresets.forEach { amount ->
                    val isSelected = budgetFcfa == amount
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.updateBudgetFcfa(amount) },
                        label = {
                            Text(
                                text = "%,d FCFA".format(amount).replace(',', ' '),
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = TypographySecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) PrimaryBlue else BorderSubtle,
                            selectedBorderColor = PrimaryBlue,
                            borderWidth = 1.dp
                        ),
                        modifier = Modifier.testTag("budget_chip_$amount")
                    )
                }
            }

            // Results Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ESTIMATION POUR ${"%,d FCFA".format(budgetFcfa).replace(',', ' ')}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = PrimaryBlue
                        )
                        Surface(
                            color = Color(0xFF10B981).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "ALGORITHME LOCAL",
                                color = Color(0xFF047857),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    // Metric grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Metric 1: Reach
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = PrimaryBlueContainer.copy(alpha = 0.35f))
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "PORTÉE ESTIMÉE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp,
                                    color = PrimaryBlue
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${"%,d".format(simulation.minReach).replace(',', ' ')} - ${"%,d".format(simulation.maxReach).replace(',', ' ')}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TypographyPrimary
                                )
                                Text(
                                    text = "personnes touchées",
                                    fontSize = 10.sp,
                                    color = TypographyMuted
                                )
                            }
                        }

                        // Metric 2: Contacts WhatsApp
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = AccentWhatsAppBg)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "CONTACTS ESTIMÉS",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp,
                                    color = AccentWhatsAppText
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${simulation.minClicks} à ${simulation.maxClicks}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = AccentWhatsAppText
                                )
                                Text(
                                    text = "prospects WhatsApp",
                                    fontSize = 10.sp,
                                    color = AccentWhatsAppText.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }

                    // Cost per prospect highlight
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Coût moyen estimé par prospect : environ ${simulation.estimatedCostPerLeadFcfa} FCFA",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TypographyPrimary
                            )
                        }
                    }

                    // Recommendations
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "MEILLEURS CRÉNEAUX DE DIFFUSION :",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp,
                                color = TypographyMuted
                            )
                        }
                        Text(
                            text = simulation.bestPostingTimes,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TypographySecondary
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = VibrantOrange,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CIBLAGE D'AUDIENCE RECOMMANDÉ :",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp,
                                color = TypographyMuted
                            )
                        }
                        Text(
                            text = simulation.recommendedAudience,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TypographySecondary
                        )
                    }
                }
            }
        }

        // ==========================================
        // SECTION 2: KIT MESSAGES CLIENTS WHATSAPP
        // ==========================================
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = null,
                    tint = Color(0xFF25D366),
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MESSAGES CLIENTS WHATSAPP (SAV & CRM)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp,
                    color = TypographyPrimary
                )
            }

            Text(
                text = "Des modèles de réponses automatiques personnalisés pour ne perdre aucune vente dès qu'un client vous écrit :",
                fontSize = 12.sp,
                color = TypographySecondary
            )

            clientMessages.forEach { msg ->
                ClientMessageCard(
                    message = msg,
                    whatsappNumber = currentAd.whatsappNumber,
                    context = context
                )
            }
        }
    }
}

@Composable
fun ClientMessageCard(
    message: ClientMessage,
    whatsappNumber: String,
    context: Context,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = PrimaryBlueContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = message.category,
                        color = PrimaryBlue,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "Prêt à envoyer",
                    fontSize = 10.sp,
                    color = TypographyMuted,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = message.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = TypographyPrimary,
                letterSpacing = (-0.3).sp
            )

            Text(
                text = message.description,
                fontSize = 11.sp,
                color = TypographyMuted,
                lineHeight = 15.sp
            )

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = message.messageText,
                    fontSize = 12.sp,
                    color = TypographySecondary,
                    lineHeight = 17.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(12.dp)
                )
            }

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText(message.title, message.messageText))
                        Toast.makeText(context, "Message copié dans le presse-papier !", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("copy_message_${message.id}"),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, PrimaryBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "COPIER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }

                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, message.messageText)
                            setPackage("com.whatsapp")
                        }
                        try {
                            context.startActivity(shareIntent)
                        } catch (e: Exception) {
                            val generalShare = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, message.messageText)
                            }
                            context.startActivity(Intent.createChooser(generalShare, "Envoyer le message"))
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("send_whatsapp_${message.id}"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WHATSAPP",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

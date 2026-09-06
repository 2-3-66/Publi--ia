package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.generator.MarketingServices
import com.example.data.model.FlyerConfig
import com.example.data.model.FlyerFormat
import com.example.data.model.FlyerTheme
import com.example.data.model.GeneratedAd
import com.example.ui.GenerationState
import com.example.ui.PubliIaViewModel
import com.example.ui.ScreenTab
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.NavyHeaderDark
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.TypographyMuted
import com.example.ui.theme.TypographyPrimary
import com.example.ui.theme.TypographySecondary
import com.example.ui.theme.VibrantOrange

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudioScreen(
    viewModel: PubliIaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val genState by viewModel.generationState.collectAsState()
    val flyerConfig by viewModel.flyerConfig.collectAsState()
    val formState by viewModel.formState.collectAsState()

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

    val headlineTags = listOf(
        "GRAND ARRIVAGE",
        "PROMO EXCLUSIVE",
        "BON PLAN DU JOUR",
        "VENTE FLASH",
        "MENU DU JOUR",
        "OUVERTURE OFFICIELLE",
        "ÉVÉNEMENT SPÉCIAL"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // --- Header Section with Showcase Banner ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = NavyHeaderDark),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_flyer_showcase),
                        contentDescription = "Studio Affiches IA",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        NavyHeaderDark.copy(alpha = 0.95f)
                                    )
                                )
                            )
                    )
                    Surface(
                        color = Color(0xFF2563EB).copy(alpha = 0.9f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "STUDIO VISUEL IA",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Générateur d'Affiches & Flyers Publicitaires",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-0.4).sp
                    )
                    Text(
                        text = "Créez en direct des affiches percutantes pour vos statuts WhatsApp, stories Instagram et publications Facebook avec tarifs en FCFA et contacts intégrés.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // --- Customization Options: Accroche / Headline Tag ---
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "1. CHOISIR LE RUBAN D'ACCROCHE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = TypographyMuted
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                headlineTags.forEach { tag ->
                    val isSelected = flyerConfig.headlineTag == tag
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.updateFlyerHeadlineTag(tag) },
                        label = {
                            Text(
                                text = tag,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium
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
                        )
                    )
                }
            }
        }

        // --- Customization Options: Visual Theme ---
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "2. THÈME VISUEL & AMBIANCE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = TypographyMuted
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FlyerTheme.entries.forEach { theme ->
                    val isSelected = flyerConfig.theme == theme
                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { viewModel.updateFlyerTheme(theme) }
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) PrimaryBlue else BorderSubtle,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                Color(theme.primaryColorHex),
                                                Color(theme.accentColorHex)
                                            )
                                        )
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = theme.label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                color = if (isSelected) PrimaryBlue else TypographyPrimary
                            )
                        }
                    }
                }
            }
        }

        // --- Customization Options: Format Selector ---
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "3. FORMAT D'AFFICHAGE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = TypographyMuted
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FlyerFormat.entries.forEach { format ->
                    val isSelected = flyerConfig.format == format
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { viewModel.updateFlyerFormat(format) }
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) PrimaryBlue else BorderSubtle,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) PrimaryBlueContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = format.label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = if (isSelected) PrimaryBlue else TypographyPrimary,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = format.subLabel,
                                fontSize = 9.sp,
                                color = TypographyMuted,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // --- THE LIVE FLYER CANVAS (Dynamic rendering) ---
        Text(
            text = "APERÇU DE VOTRE AFFICHE EN DIRECT",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            color = TypographyMuted
        )

        FlyerCardPreview(
            ad = currentAd,
            config = flyerConfig,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .testTag("flyer_card_preview")
        )

        // --- Action Buttons ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    val caption = MarketingServices.generateFlyerCaption(currentAd, flyerConfig)
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "Affiche Publicitaire - ${currentAd.businessName}")
                        putExtra(Intent.EXTRA_TEXT, caption)
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Partager l'affiche"))
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("share_flyer_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VibrantOrange),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PARTAGER L'AFFICHE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp,
                    color = Color.White
                )
            }

            OutlinedButton(
                onClick = {
                    val caption = MarketingServices.generateFlyerCaption(currentAd, flyerConfig)
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Flyer Caption", caption))
                    Toast.makeText(context, "Texte du flyer copié !", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("copy_flyer_text_button"),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.5.dp, PrimaryBlue),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "COPIER LE TEXTE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // --- Fast Navigation to Other Services ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryBlueContainer.copy(alpha = 0.4f)),
            border = BorderStroke(1.dp, PrimaryBlue.copy(alpha = 0.2f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "BESOIN DE MESSAGES DE VENTE WHATSAPP ?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = PrimaryBlue,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Accédez aux messages automatiques d'accueil, de relance et de confirmation.",
                        fontSize = 11.sp,
                        color = TypographySecondary,
                        lineHeight = 15.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { viewModel.setTab(ScreenTab.SERVICES) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("VOIR", fontSize = 11.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun FlyerCardPreview(
    ad: GeneratedAd,
    config: FlyerConfig,
    modifier: Modifier = Modifier
) {
    val theme = config.theme
    val primaryGrad = Brush.verticalGradient(
        colors = listOf(
            Color(theme.primaryColorHex),
            Color(theme.secondaryColorHex)
        )
    )
    val accentColor = Color(theme.accentColorHex)
    val textColor = Color(theme.textColorHex)

    val heightDp = when (config.format) {
        FlyerFormat.SQUARE -> 340.dp
        FlyerFormat.STORY -> 420.dp
        FlyerFormat.BANNER -> 260.dp
    }

    Card(
        modifier = modifier
            .height(heightDp)
            .shadow(12.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(2.dp, accentColor.copy(alpha = 0.6f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(primaryGrad)
                .padding(18.dp)
        ) {
            // Background decorative circles
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.TopEnd)
                    .background(accentColor.copy(alpha = 0.08f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .align(Alignment.BottomStart)
                    .background(Color.White.copy(alpha = 0.04f), CircleShape)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top: Headline Ribbon & Activity Tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = accentColor,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ElectricBolt,
                                contentDescription = null,
                                tint = Color(theme.primaryColorHex),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = config.headlineTag,
                                color = Color(theme.primaryColorHex),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = ad.city.uppercase(),
                            color = textColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Middle: Business Name, Slogan & Offer Badge
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = ad.businessName.uppercase(),
                            color = textColor,
                            fontSize = if (config.format == FlyerFormat.BANNER) 20.sp else 24.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-0.5).sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Vérifié",
                            tint = accentColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Text(
                        text = "« ${ad.slogan} »",
                        color = accentColor,
                        fontSize = if (config.format == FlyerFormat.BANNER) 13.sp else 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Price and Promo Tag Highlight
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (ad.price.isNotBlank()) {
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp),
                                shadowElevation = 4.dp
                            ) {
                                Text(
                                    text = ad.price,
                                    color = Color(theme.primaryColorHex),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }

                        if (ad.specialOffer.isNotBlank()) {
                            Surface(
                                color = accentColor.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, accentColor)
                            ) {
                                Text(
                                    text = "🎁 ${ad.specialOffer}",
                                    color = textColor,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }
                }

                // Bottom: Address & WhatsApp Strip
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    HorizontalDivider(color = Color.White.copy(alpha = 0.2f), thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Location info
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = ad.address.ifBlank { ad.city },
                                color = textColor.copy(alpha = 0.9f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // WhatsApp Contact Chip
                        if (ad.whatsappNumber.isNotBlank()) {
                            Surface(
                                color = Color(0xFF25D366),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Chat,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = ad.whatsappNumber,
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

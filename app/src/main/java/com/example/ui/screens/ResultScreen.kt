package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GeneratedAd
import com.example.ui.GenerationState
import com.example.ui.PubliIaViewModel
import com.example.ui.ScreenTab
import com.example.ui.components.ActionHelpers
import com.example.ui.theme.AccentCallBg
import com.example.ui.theme.AccentCallText
import com.example.ui.theme.AccentItineraryBg
import com.example.ui.theme.AccentItineraryText
import com.example.ui.theme.AccentWhatsAppBg
import com.example.ui.theme.AccentWhatsAppText
import com.example.ui.theme.ActionButtonNeutralBg
import com.example.ui.theme.ActionButtonNeutralText
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.VibrantOrange
import com.example.ui.theme.CardFacebookBackground
import com.example.ui.theme.CardFacebookBorder
import com.example.ui.theme.CardTikTokBackground
import com.example.ui.theme.CardTikTokBorder
import com.example.ui.theme.CardVideoBackground
import com.example.ui.theme.CardVideoBorder
import com.example.ui.theme.CardVideoText
import com.example.ui.theme.CardWhatsAppBackground
import com.example.ui.theme.CardWhatsAppBorder
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.TypographyMuted
import com.example.ui.theme.TypographyPrimary
import com.example.ui.theme.TypographySecondary

@Composable
fun ResultScreen(
    viewModel: PubliIaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val genState by viewModel.generationState.collectAsState()

    val currentAd: GeneratedAd? = when (val s = genState) {
        is GenerationState.Success -> s.ad
        else -> null
    }

    if (currentAd == null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Aucune publicité générée pour le moment.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TypographyMuted
                )
                Button(
                    onClick = { viewModel.setTab(ScreenTab.CREATE) },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("CRÉER UNE PUBLICITÉ", fontWeight = FontWeight.Black, letterSpacing = 1.sp)
                }
            }
        }
        return
    }

    val fullLocationQuery = buildString {
        if (currentAd.address.isNotBlank()) append(currentAd.address)
        if (currentAd.landmark.isNotBlank()) {
            if (isNotEmpty()) append(", ")
            append(currentAd.landmark)
        }
        if (currentAd.city.isNotBlank()) {
            if (isNotEmpty()) append(", ")
            append(currentAd.city)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // --- Campaign Header Badge & Business Name ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, BorderSubtle),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        color = PrimaryBlueContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = currentAd.activityType.uppercase(),
                            color = PrimaryBlue,
                            fontWeight = FontWeight.Black,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "✨ PRÊT À DIFFUSER",
                        color = TypographyMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Text(
                    text = currentAd.businessName,
                    color = TypographyPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.5).sp
                )
            }
        }

        // --- Bold Slogan Card (Featured Cobalt Blue) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("card_slogan"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = PrimaryBlue),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SLOGAN DE MARQUE",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.5.sp
                    )
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = currentAd.slogan.ifBlank { "L'EXCELLENCE QUI FAIT LA DIFFÉRENCE !" },
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 32.sp,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { ActionHelpers.copyToClipboard(context, currentAd.slogan, "Slogan") },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .testTag("copy_slogan_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copier le slogan",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { ActionHelpers.shareText(context, currentAd.slogan, "Partager le slogan") },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                            .testTag("share_slogan_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Partager le slogan",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // --- NEW SERVICES SHORTCUTS BANNER ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, PrimaryBlue.copy(alpha = 0.3f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = VibrantOrange,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "NOUVEAUX SERVICES PUBLI-IA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = TypographyPrimary
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { viewModel.setTab(ScreenTab.STUDIO) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("shortcut_studio_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = VibrantOrange)
                    ) {
                        Text(
                            text = "🎨 CRÉER L'AFFICHE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp,
                            color = Color.White
                        )
                    }

                    OutlinedButton(
                        onClick = { viewModel.setTab(ScreenTab.SERVICES) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("shortcut_services_button"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, PrimaryBlue),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryBlue)
                    ) {
                        Text(
                            text = "⚡ CRM & BUDGET",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // --- Location Card with Direct Itinerary Button ---
        if (fullLocationQuery.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, BorderSubtle),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "LOCALISATION",
                            color = TypographyMuted,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = fullLocationQuery,
                            color = TypographyPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = { ActionHelpers.openMapDirections(context, fullLocationQuery) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentItineraryBg,
                            contentColor = AccentItineraryText
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.testTag("itinerary_action_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Directions,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "ITINÉRAIRE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // --- Output 1: Publicité WhatsApp ---
        BoldThemedCard(
            badge = "WHATSAPP",
            subtitle = "Idéale pour vos statuts, listes de diffusion et groupes",
            icon = Icons.AutoMirrored.Filled.Chat,
            containerColor = CardWhatsAppBackground,
            borderColor = CardWhatsAppBorder,
            headerTextColor = TypographySecondary,
            content = currentAd.whatsappAd,
            testTagPrefix = "whatsapp",
            onCopy = { ActionHelpers.copyToClipboard(context, currentAd.whatsappAd, "WhatsApp") },
            onShare = { ActionHelpers.shareText(context, currentAd.whatsappAd, "Partager sur WhatsApp") }
        )

        // --- Output 2: Publicité Facebook ---
        BoldThemedCard(
            badge = "FACEBOOK",
            subtitle = "Détaillée avec accroche, avantages et hashtags pour vos publications",
            icon = Icons.Default.Public,
            containerColor = CardFacebookBackground,
            borderColor = CardFacebookBorder,
            headerTextColor = TypographySecondary,
            content = currentAd.facebookAd,
            testTagPrefix = "facebook",
            onCopy = { ActionHelpers.copyToClipboard(context, currentAd.facebookAd, "Facebook") },
            onShare = { ActionHelpers.shareText(context, currentAd.facebookAd, "Partager sur Facebook") }
        )

        // --- Output 3: Texte Court TikTok ---
        BoldThemedCard(
            badge = "TIKTOK",
            subtitle = "Accroche percutante dans les 3 premières secondes et format viral",
            icon = Icons.Default.MusicNote,
            containerColor = CardTikTokBackground,
            borderColor = CardTikTokBorder,
            headerTextColor = Color(0xFF7D3434),
            content = currentAd.tiktokText,
            testTagPrefix = "tiktok",
            onCopy = { ActionHelpers.copyToClipboard(context, currentAd.tiktokText, "TikTok") },
            onShare = { ActionHelpers.shareText(context, currentAd.tiktokText, "Partager pour TikTok") }
        )

        // --- Output 4: Script Vidéo Publicitaire ---
        BoldThemedCard(
            badge = "SCRIPT VIDÉO (30S)",
            subtitle = "Scénario plan par plan avec consignes visuelles et voix-off",
            icon = Icons.Default.Movie,
            containerColor = CardVideoBackground,
            borderColor = CardVideoBorder,
            headerTextColor = CardVideoText,
            content = currentAd.videoScript,
            testTagPrefix = "video_script",
            isScript = true,
            onCopy = { ActionHelpers.copyToClipboard(context, currentAd.videoScript, "Script Vidéo") },
            onShare = { ActionHelpers.shareText(context, currentAd.videoScript, "Partager le script vidéo") }
        )

        // --- Quick 4-Action Grid: Copier, Partager, Appeler, WhatsApp ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, BorderSubtle),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Button: Copier tout
                Button(
                    onClick = {
                        val allText = buildString {
                            appendLine("=== PUBLICITÉ ${currentAd.businessName} ===")
                            appendLine("\n📢 Slogan : ${currentAd.slogan}")
                            appendLine("\n📱 WhatsApp :\n${currentAd.whatsappAd}")
                            appendLine("\n🌐 Facebook :\n${currentAd.facebookAd}")
                            appendLine("\n🎵 TikTok :\n${currentAd.tiktokText}")
                            appendLine("\n🎬 Script Vidéo :\n${currentAd.videoScript}")
                        }
                        ActionHelpers.copyToClipboard(context, allText, "Campagne Complète")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ActionButtonNeutralBg,
                        contentColor = ActionButtonNeutralText
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("COPIER", fontSize = 9.sp, fontWeight = FontWeight.Black)
                    }
                }

                // Button: Partager tout
                Button(
                    onClick = {
                        val allText = buildString {
                            appendLine("=== PUBLICITÉ ${currentAd.businessName} ===")
                            appendLine("\n📢 Slogan : ${currentAd.slogan}")
                            appendLine("\n📱 WhatsApp :\n${currentAd.whatsappAd}")
                            appendLine("\n🌐 Facebook :\n${currentAd.facebookAd}")
                        }
                        ActionHelpers.shareText(context, allText, "Partager la campagne")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ActionButtonNeutralBg,
                        contentColor = ActionButtonNeutralText
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("PARTAGER", fontSize = 9.sp, fontWeight = FontWeight.Black)
                    }
                }

                // Button: Appeler
                if (currentAd.phoneNumber.isNotBlank()) {
                    Button(
                        onClick = { ActionHelpers.openDialer(context, currentAd.phoneNumber) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentCallBg,
                            contentColor = AccentCallText
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("call_action_button")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("APPELER", fontSize = 9.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }

                // Button: WhatsApp
                if (currentAd.whatsappNumber.isNotBlank()) {
                    Button(
                        onClick = {
                            ActionHelpers.openWhatsApp(
                                context,
                                currentAd.whatsappNumber,
                                "Bonjour ! Je vous contacte concernant votre annonce chez ${currentAd.businessName}."
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentWhatsAppBg,
                            contentColor = AccentWhatsAppText
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("whatsapp_action_button")
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("WHATSAPP", fontSize = 9.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }

        // --- Bottom Main Button: Create another ad ---
        Button(
            onClick = { viewModel.setTab(ScreenTab.CREATE) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("create_another_button"),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "CRÉER UNE AUTRE PUBLICITÉ",
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun BoldThemedCard(
    badge: String,
    subtitle: String,
    icon: ImageVector,
    containerColor: Color,
    borderColor: Color,
    headerTextColor: Color,
    content: String,
    testTagPrefix: String,
    isScript: Boolean = false,
    onCopy: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("card_$testTagPrefix"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = headerTextColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = badge,
                        color = headerTextColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                Row {
                    IconButton(
                        onClick = onCopy,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.5f))
                            .testTag("copy_${testTagPrefix}_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copier",
                            tint = TypographyPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = onShare,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.5f))
                            .testTag("share_${testTagPrefix}_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Partager",
                            tint = TypographyPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = Color.White.copy(alpha = 0.7f),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = content,
                    modifier = Modifier.padding(14.dp),
                    style = if (isScript) {
                        MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )
                    } else {
                        MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )
                    },
                    color = TypographyPrimary,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

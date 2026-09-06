package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.OnPrimaryBlueContainer
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.TypographyMuted
import com.example.ui.theme.TypographyPrimary

object ActionHelpers {

    fun copyToClipboard(context: Context, text: String, label: String = "Publicité") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copié dans le presse-papier !", Toast.LENGTH_SHORT).show()
    }

    fun shareText(context: Context, text: String, title: String = "Partager la publicité") {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, title)
        context.startActivity(shareIntent)
    }

    fun openWhatsApp(context: Context, rawNumber: String, prefilledText: String = "") {
        val cleanNumber = rawNumber.replace(Regex("[^0-9+]"), "")
        val url = if (cleanNumber.isNotBlank()) {
            val encodedMsg = Uri.encode(prefilledText)
            if (cleanNumber.startsWith("+")) {
                "https://wa.me/${cleanNumber.removePrefix("+")}?text=$encodedMsg"
            } else {
                "https://wa.me/$cleanNumber?text=$encodedMsg"
            }
        } else {
            "https://wa.me/"
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Impossible d'ouvrir WhatsApp : application non trouvée", Toast.LENGTH_SHORT).show()
        }
    }

    fun openDialer(context: Context, rawNumber: String) {
        val cleanNumber = rawNumber.replace(Regex("[^0-9+]"), "")
        if (cleanNumber.isBlank()) {
            Toast.makeText(context, "Numéro de téléphone non renseigné", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber"))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Impossible d'ouvrir le composeur", Toast.LENGTH_SHORT).show()
        }
    }

    fun openMapDirections(context: Context, addressQuery: String) {
        if (addressQuery.isBlank()) {
            Toast.makeText(context, "Adresse ou localisation non renseignée", Toast.LENGTH_SHORT).show()
            return
        }
        val encoded = Uri.encode(addressQuery)
        val geoUri = Uri.parse("geo:0,0?q=$encoded")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        try {
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            // Fallback to browser Google Maps
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=$encoded"))
            try {
                context.startActivity(webIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "Impossible d'ouvrir la carte", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun PubliTopBar(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("publi_top_bar"),
        color = CanvasBackground
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "ASSISTANT IA",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "PUBLI-IA",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = TypographyPrimary,
                        letterSpacing = (-0.75).sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlueContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = "Assistant IA",
                        tint = OnPrimaryBlueContainer,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            HorizontalDivider(
                color = BorderSubtle,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun SectionContainer(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, BorderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PrimaryBlueContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = PrimaryBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = TypographyPrimary
                )
            }

            content()
        }
    }
}

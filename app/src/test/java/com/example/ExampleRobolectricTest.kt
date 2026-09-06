package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.generator.AdGenerator
import com.example.data.model.AdRequest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("PUBLI-IA", appName)
  }

  @Test
  fun `test local ad generator outputs all 5 required formats`() {
    val generator = AdGenerator()
    val request = AdRequest(
      businessName = "Chez Tantie Alice",
      activityType = "Maquis & Restaurant",
      productOrService = "Poulet braisé",
      city = "Abidjan",
      price = "3 500 FCFA"
    )
    val result = generator.generateLocalAd(request)
    assertTrue("WhatsApp ad must not be blank", result.whatsappAd.isNotBlank())
    assertTrue("Facebook ad must not be blank", result.facebookAd.isNotBlank())
    assertTrue("TikTok text must not be blank", result.tiktokText.isNotBlank())
    assertTrue("Slogan must not be blank", result.slogan.isNotBlank())
    assertTrue("Video script must not be blank", result.videoScript.isNotBlank())
    assertTrue("Should include price in FCFA", result.whatsappAd.contains("3 500 FCFA"))
  }
}


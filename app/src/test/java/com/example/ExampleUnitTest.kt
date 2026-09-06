package com.example

import com.example.data.generator.MarketingServices
import com.example.data.model.FlyerConfig
import com.example.data.model.GeneratedAd
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testClientMessagesGeneration() {
    val ad = GeneratedAd(
      businessName = "Chez Tantie Alice",
      activityType = "Maquis & Restaurant",
      whatsappAd = "Spécialité Poulet Braisé & Alloco",
      facebookAd = "Venez déguster le meilleur poulet",
      tiktokText = "Le meilleur d'Abidjan",
      slogan = "Le goût authentique qui rassemble",
      videoScript = "Spot vidéo",
      city = "Abidjan",
      address = "Cocody Angré",
      whatsappNumber = "+225 07 00 11 22 33",
      price = "3 500 FCFA"
    )

    val messages = MarketingServices.generateClientMessages(ad)
    assertEquals(4, messages.size)
    assertTrue("Should contain welcome message", messages.any { it.id == "welcome" })
    assertTrue("Should contain follow-up message", messages.any { it.id == "followup" })
    assertTrue("Should contain order confirmation", messages.any { it.id == "order_confirm" })
    assertTrue("Should contain loyalty message", messages.any { it.id == "loyalty" })
    assertTrue("Should mention business name in welcome message", messages.first().messageText.contains("Chez Tantie Alice"))
  }

  @Test
  fun testBudgetSimulation() {
    val simulation = MarketingServices.simulateBudget(
      budgetFcfa = 5000,
      activityType = "Maquis & Restaurant",
      city = "Abidjan"
    )

    assertTrue("Min reach should be positive", simulation.minReach > 0)
    assertTrue("Max reach should be greater than min reach", simulation.maxReach > simulation.minReach)
    assertTrue("Clicks should be positive", simulation.minClicks > 0)
    assertTrue("Should have recommendations", simulation.strategicTips.isNotEmpty())
    assertTrue("Should have posting times", simulation.bestPostingTimes.isNotBlank())
  }
}

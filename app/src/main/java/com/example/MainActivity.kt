package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.sp
import com.example.ui.GenerationState
import com.example.ui.PubliIaViewModel
import com.example.ui.ScreenTab
import com.example.ui.components.PubliTopBar
import com.example.ui.screens.CreateScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.screens.StudioScreen
import androidx.compose.ui.text.font.FontWeight
import com.example.ui.theme.OnPrimaryBlueContainer
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.PrimaryBlueContainer
import com.example.ui.theme.TypographyMuted
import com.example.ui.theme.TypographyPrimary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

  private val viewModel: PubliIaViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        PubliIaApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun PubliIaApp(
  viewModel: PubliIaViewModel,
  modifier: Modifier = Modifier
) {
  val currentTab by viewModel.currentTab.collectAsState()
  val genState by viewModel.generationState.collectAsState()
  val savedAds by viewModel.savedAds.collectAsState()

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      Column(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        PubliTopBar()
      }
    },
    bottomBar = {
      NavigationBar(
        modifier = Modifier
          .fillMaxWidth()
          .windowInsetsPadding(WindowInsets.navigationBars)
          .testTag("publi_bottom_nav"),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
      ) {
        NavigationBarItem(
          selected = currentTab == ScreenTab.CREATE,
          onClick = { viewModel.setTab(ScreenTab.CREATE) },
          icon = {
            Icon(imageVector = Icons.Default.EditNote, contentDescription = "Créer")
          },
          label = { Text("Créer", fontSize = 10.sp, fontWeight = if (currentTab == ScreenTab.CREATE) FontWeight.Black else FontWeight.Medium) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = OnPrimaryBlueContainer,
            indicatorColor = PrimaryBlueContainer,
            selectedTextColor = PrimaryBlue,
            unselectedIconColor = TypographyMuted,
            unselectedTextColor = TypographyMuted
          ),
          modifier = Modifier.testTag("nav_tab_create")
        )

        NavigationBarItem(
          selected = currentTab == ScreenTab.RESULT,
          onClick = { viewModel.setTab(ScreenTab.RESULT) },
          icon = {
            BadgedBox(
              badge = {
                if (genState is GenerationState.Success) {
                  Badge(containerColor = PrimaryBlue) {
                    Text("1", color = Color.White)
                  }
                }
              }
            ) {
              Icon(imageVector = Icons.Default.Campaign, contentDescription = "Campagne")
            }
          },
          label = { Text("Campagne", fontSize = 10.sp, fontWeight = if (currentTab == ScreenTab.RESULT) FontWeight.Black else FontWeight.Medium) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = OnPrimaryBlueContainer,
            indicatorColor = PrimaryBlueContainer,
            selectedTextColor = PrimaryBlue,
            unselectedIconColor = TypographyMuted,
            unselectedTextColor = TypographyMuted
          ),
          modifier = Modifier.testTag("nav_tab_result")
        )

        NavigationBarItem(
          selected = currentTab == ScreenTab.STUDIO,
          onClick = { viewModel.setTab(ScreenTab.STUDIO) },
          icon = {
            Icon(imageVector = Icons.Default.Palette, contentDescription = "Affiches")
          },
          label = { Text("Affiches", fontSize = 10.sp, fontWeight = if (currentTab == ScreenTab.STUDIO) FontWeight.Black else FontWeight.Medium) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = OnPrimaryBlueContainer,
            indicatorColor = PrimaryBlueContainer,
            selectedTextColor = PrimaryBlue,
            unselectedIconColor = TypographyMuted,
            unselectedTextColor = TypographyMuted
          ),
          modifier = Modifier.testTag("nav_tab_studio")
        )

        NavigationBarItem(
          selected = currentTab == ScreenTab.SERVICES,
          onClick = { viewModel.setTab(ScreenTab.SERVICES) },
          icon = {
            Icon(imageVector = Icons.AutoMirrored.Filled.TrendingUp, contentDescription = "Services")
          },
          label = { Text("Services", fontSize = 10.sp, fontWeight = if (currentTab == ScreenTab.SERVICES) FontWeight.Black else FontWeight.Medium) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = OnPrimaryBlueContainer,
            indicatorColor = PrimaryBlueContainer,
            selectedTextColor = PrimaryBlue,
            unselectedIconColor = TypographyMuted,
            unselectedTextColor = TypographyMuted
          ),
          modifier = Modifier.testTag("nav_tab_services")
        )

        NavigationBarItem(
          selected = currentTab == ScreenTab.HISTORY,
          onClick = { viewModel.setTab(ScreenTab.HISTORY) },
          icon = {
            BadgedBox(
              badge = {
                if (savedAds.isNotEmpty()) {
                  Badge(containerColor = PrimaryBlue) {
                    Text("${savedAds.size}", color = Color.White)
                  }
                }
              }
            ) {
              Icon(imageVector = Icons.Default.History, contentDescription = "Historique")
            }
          },
          label = { Text("Historique", fontSize = 10.sp, fontWeight = if (currentTab == ScreenTab.HISTORY) FontWeight.Black else FontWeight.Medium) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = OnPrimaryBlueContainer,
            indicatorColor = PrimaryBlueContainer,
            selectedTextColor = PrimaryBlue,
            unselectedIconColor = TypographyMuted,
            unselectedTextColor = TypographyMuted
          ),
          modifier = Modifier.testTag("nav_tab_history")
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
      ) {
        when (currentTab) {
          ScreenTab.CREATE -> CreateScreen(viewModel = viewModel)
          ScreenTab.RESULT -> ResultScreen(viewModel = viewModel)
          ScreenTab.STUDIO -> StudioScreen(viewModel = viewModel)
          ScreenTab.SERVICES -> ServicesScreen(viewModel = viewModel)
          ScreenTab.HISTORY -> HistoryScreen(viewModel = viewModel)
        }
      }
    }
  }
}


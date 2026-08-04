package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ReviewScreen
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.GatViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        GatApp()
      }
    }
  }
}

sealed class NavTab(val index: Int, val title: String, val icon: ImageVector) {
  object Home : NavTab(0, "Home", Icons.Default.Explore)
  object Practice : NavTab(1, "Practice", Icons.Default.FitnessCenter)
  object Review : NavTab(2, "Review", Icons.Default.Psychology)
  object Library : NavTab(3, "Library", Icons.AutoMirrored.Filled.MenuBook)
  object Progress : NavTab(4, "Progress", Icons.Default.TrendingUp)
}

@Composable
fun GatApp() {
  val viewModel: GatViewModel = viewModel()
  val uiState by viewModel.uiState.collectAsState()
  var currentTab by remember { mutableStateOf<NavTab>(NavTab.Home) }

  val tabs = listOf(
    NavTab.Home,
    NavTab.Practice,
    NavTab.Review,
    NavTab.Library,
    NavTab.Progress
  )

  val activeQuiz = uiState.activeQuiz

  if (activeQuiz != null) {
    // Fullscreen Quiz View
    QuizScreen(quiz = activeQuiz, viewModel = viewModel)
  } else {
    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
      bottomBar = {
        NavigationBar(
          containerColor = DarkCardBg,
          tonalElevation = 8.dp,
          modifier = Modifier
            .border(1.dp, DarkCardBorder)
            .testTag("bottom_nav_bar")
        ) {
          tabs.forEach { tab ->
            val isSelected = currentTab.index == tab.index
            NavigationBarItem(
              selected = isSelected,
              onClick = { currentTab = tab },
              icon = {
                Icon(
                  imageVector = tab.icon,
                  contentDescription = tab.title,
                  modifier = Modifier.size(22.dp)
                )
              },
              label = {
                Text(
                  text = tab.title,
                  fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
                  fontSize = 11.sp
                )
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF0F0E17),
                selectedTextColor = GoldPrimary,
                indicatorColor = GoldPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        AnimatedContent(
          targetState = currentTab,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "TabTransition"
        ) { targetTab ->
          when (targetTab) {
            NavTab.Home -> HomeScreen(
              viewModel = viewModel,
              onNavigateTab = { targetIdx ->
                currentTab = tabs.firstOrNull { it.index == targetIdx } ?: NavTab.Home
              }
            )
            NavTab.Practice -> PracticeScreen(viewModel = viewModel)
            NavTab.Review -> ReviewScreen(viewModel = viewModel)
            NavTab.Library -> LibraryScreen(viewModel = viewModel)
            NavTab.Progress -> ProgressScreen(viewModel = viewModel)
          }
        }
      }
    }
  }
}


package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Elegant Dark Palette Tokens
val DarkBackground = Color(0xFF0F0E17)
val DarkSurface = Color(0xFF181622)
val DarkSurfaceElevated = Color(0xFF221F33)
val DarkSurfaceVariant = Color(0xFF2D2942)
val DarkOutline = Color(0xFF4A4468)
val DarkOutlineSubtle = Color(0x334A4468)

// Core Palette for GAT Master
val GoldPrimary = Color(0xFFD4AF37)
val GoldSecondary = Color(0xFFFFD54F)
val PrimaryAccent = Color(0xFF8B80F9)
val IndigoSurface = Color(0xFF1E1B38)
val DarkCardBg = Color(0xFF161424)
val DarkCardBorder = Color(0xFF2C2844)
val EmeraldPass = Color(0xFF00C896)
val RoseAlert = Color(0xFFFF5252)
val AmberBoss = Color(0xFFFF9F1C)
val CyanAccent = Color(0xFF00D2FF)

val ElegantPrimary = GoldPrimary
val ElegantOnPrimary = Color(0xFF0F0E17)
val ElegantPrimaryContainer = Color(0xFF383015)
val ElegantOnPrimaryContainer = Color(0xFFFFE082)

val ElegantSecondary = PrimaryAccent
val ElegantOnSecondary = Color(0xFFFFFFFF)
val ElegantSecondaryContainer = Color(0xFF2E2A54)
val ElegantOnSecondaryContainer = Color(0xFFD9D4FF)

val ElegantTertiary = CyanAccent
val ElegantOnTertiary = Color(0xFF00363F)
val ElegantTertiaryContainer = Color(0xFF004E5B)
val ElegantOnTertiaryContainer = Color(0xFFB8EAFF)

val TextPrimary = Color(0xFFF5F4F8)
val TextSecondary = Color(0xFFB5B2C8)
val TextMuted = Color(0xFF7A7694)

// Accent highlights
val AccentGold = GoldPrimary
val AccentEmerald = EmeraldPass
val AccentRose = RoseAlert
val AccentSky = CyanAccent
val AccentAmber = AmberBoss

object Gradients {
    val goldBronze = Brush.horizontalGradient(
        colors = listOf(Color(0xFFD4AF37), Color(0xFFFFD54F))
    )
    val royalIndigo = Brush.verticalGradient(
        colors = listOf(Color(0xFF24224A), Color(0xFF141328))
    )
    val bossAmber = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFF9F1C), Color(0xFFFF5252))
    )
}

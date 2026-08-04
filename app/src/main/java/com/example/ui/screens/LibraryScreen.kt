package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.models.CheatSheetItem
import com.example.data.models.GatTrapWord
import com.example.data.models.VocabTier
import com.example.data.models.VocabWord
import com.example.data.repository.CheatSheetsData
import com.example.data.repository.GatTrapsData
import com.example.data.repository.VocabClustersData
import com.example.ui.theme.AmberBoss
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.EmeraldPass
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldSecondary
import com.example.ui.theme.IndigoSurface
import com.example.ui.theme.PrimaryAccent
import com.example.ui.theme.RoseAlert
import com.example.ui.viewmodel.GatViewModel

@Composable
fun LibraryScreen(
    viewModel: GatViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val vocabMastery by viewModel.vocabMasteryList.collectAsState()
    val trapMastery by viewModel.trapMasteryList.collectAsState()

    val vocabMasteryMap = remember(vocabMastery) { vocabMastery.associateBy { it.wordId } }
    val trapMasteryMap = remember(trapMastery) { trapMastery.associateBy { it.trapId } }

    val allVocab = remember { VocabClustersData.completeVocabularyLexicon }
    val allClusters = remember { VocabClustersData.clusters }
    val allTraps = remember { GatTrapsData.allTraps }
    val cheatSheets = remember { CheatSheetsData.items }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "KNOWLEDGE REPOSITORY",
            style = MaterialTheme.typography.labelSmall,
            color = GoldPrimary,
            letterSpacing = 2.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "GAT Lexicon & Formula Vault",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Main Tab Row
        TabRow(
            selectedTabIndex = uiState.selectedLibrarySubTab,
            containerColor = DarkCardBg,
            contentColor = GoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[uiState.selectedLibrarySubTab]),
                    color = GoldPrimary
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = uiState.selectedLibrarySubTab == 0,
                onClick = { viewModel.setLibrarySubTab(0) },
                text = {
                    Text(
                        "Vocab (3,059)",
                        fontWeight = if (uiState.selectedLibrarySubTab == 0) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = uiState.selectedLibrarySubTab == 1,
                onClick = { viewModel.setLibrarySubTab(1) },
                text = {
                    Text(
                        "25 Traps",
                        fontWeight = if (uiState.selectedLibrarySubTab == 1) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = uiState.selectedLibrarySubTab == 2,
                onClick = { viewModel.setLibrarySubTab(2) },
                text = {
                    Text(
                        "Cheat Sheets",
                        fontWeight = if (uiState.selectedLibrarySubTab == 2) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        when (uiState.selectedLibrarySubTab) {
            0 -> {
                // 3,059 Vocab Lexicon View
                // Search Input
                OutlinedTextField(
                    value = uiState.vocabSearchQuery,
                    onValueChange = { viewModel.setVocabSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("vocab_search_input"),
                    placeholder = {
                        Text(
                            "Search 3,059 words, roots or definitions...",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = GoldPrimary
                        )
                    },
                    trailingIcon = {
                        if (uiState.vocabSearchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setVocabSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkCardBg,
                        unfocusedContainerColor = DarkCardBg,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = DarkCardBorder
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Tier Filter Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val tiers = listOf(
                        Pair("All Tiers", null),
                        Pair("Essential", VocabTier.ESSENTIAL),
                        Pair("High-Yield", VocabTier.HIGH_YIELD),
                        Pair("Expert", VocabTier.EXPERT)
                    )

                    tiers.forEach { (label, tierVal) ->
                        val isSelected = uiState.vocabTierFilter == tierVal
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.setVocabTierFilter(tierVal) },
                            color = if (isSelected) GoldPrimary else DarkCardBg,
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) GoldPrimary else DarkCardBorder)
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color(0xFF0F0E17) else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Cluster Filter Row
                ScrollableTabRow(
                    selectedTabIndex = uiState.selectedVocabCluster ?: 0,
                    containerColor = Color.Transparent,
                    edgePadding = 0.dp,
                    divider = {},
                    indicator = {}
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { viewModel.setVocabClusterFilter(null) },
                        color = if (uiState.selectedVocabCluster == null) CyanAccent else DarkCardBg,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (uiState.selectedVocabCluster == null) CyanAccent else DarkCardBorder)
                    ) {
                        Text(
                            text = "All 244 Clusters",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (uiState.selectedVocabCluster == null) Color(0xFF0F0E17) else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (uiState.selectedVocabCluster == null) FontWeight.Bold else FontWeight.Normal
                        )
                    }

                    allClusters.take(30).forEach { cluster ->
                        val isSelected = uiState.selectedVocabCluster == cluster.id
                        Surface(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { viewModel.setVocabClusterFilter(cluster.id) },
                            color = if (isSelected) CyanAccent else DarkCardBg,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) CyanAccent else DarkCardBorder)
                        ) {
                            Text(
                                text = "#${cluster.id}: ${cluster.name.take(24)}",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) Color(0xFF0F0E17) else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                val filteredWords = remember(
                    uiState.vocabSearchQuery,
                    uiState.selectedVocabCluster,
                    uiState.vocabTierFilter
                ) {
                    allVocab.filter { word ->
                        val matchesQuery = uiState.vocabSearchQuery.isEmpty() ||
                                word.word.contains(uiState.vocabSearchQuery, ignoreCase = true) ||
                                word.definition.contains(uiState.vocabSearchQuery, ignoreCase = true) ||
                                word.etymology.contains(uiState.vocabSearchQuery, ignoreCase = true)
                        val matchesCluster = uiState.selectedVocabCluster == null || word.clusterId == uiState.selectedVocabCluster
                        val matchesTier = uiState.vocabTierFilter == null || word.tier == uiState.vocabTierFilter
                        matchesQuery && matchesCluster && matchesTier
                    }
                }

                Text(
                    text = "Showing ${filteredWords.size} words:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredWords.take(150)) { vocabWord ->
                        val mastery = vocabMasteryMap[vocabWord.id]
                        val isBookmarked = mastery?.isBookmarked ?: false

                        VocabItemCard(
                            word = vocabWord,
                            boxNumber = mastery?.leitnerBox ?: 1,
                            isBookmarked = isBookmarked,
                            onToggleBookmark = {
                                viewModel.toggleVocabBookmark(vocabWord.id, !isBookmarked)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            1 -> {
                // 25 GAT Traps View
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = IndigoSurface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, RoseAlert.copy(alpha = 0.5f))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "25 High-Frequency GAT Decoy Traps",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Test-makers intentionally plant false cognates and lure words to punish superficial pattern-matching. Master these 25 essential traps.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    items(allTraps) { trap ->
                        val isMastered = trapMasteryMap[trap.id]?.isMastered ?: false
                        val isBookmarked = trapMasteryMap[trap.id]?.isBookmarked ?: false
                        var isExpanded by remember { mutableStateOf(false) }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded }
                                .testTag("trap_item_${trap.id}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkCardBg),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isMastered) EmeraldPass.copy(alpha = 0.5f) else DarkCardBorder
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = trap.word,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Surface(
                                            color = Color(0x33FF6B6B),
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "Decoy: ${trap.decoyOption}",
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                style = MaterialTheme.typography.labelSmall,
                                                color = RoseAlert,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Surface(
                                        modifier = Modifier.clickable {
                                            viewModel.toggleTrapMastery(trap.id, !isMastered)
                                        },
                                        color = if (isMastered) EmeraldPass.copy(alpha = 0.2f) else Color(0x22FFFFFF),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = if (isMastered) Icons.Default.CheckCircle else Icons.Default.Check,
                                                contentDescription = null,
                                                tint = if (isMastered) EmeraldPass else Color(0x66FFFFFF),
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = if (isMastered) "Mastered" else "Mark Done",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (isMastered) EmeraldPass else Color(0xAAFFFFFF)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = "⚠️ Misconception: ${trap.whyDeceptive}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmberBoss
                                )

                                Text(
                                    text = "💡 Real Meaning: ${trap.realDefinition}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold
                                )

                                AnimatedVisibility(visible = isExpanded) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "Deep Context & GAT Nuance:\n${trap.deepExplanation}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )

                                        Surface(
                                            color = PrimaryAccent.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(8.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "🧠 Memory Mnemonic: ${trap.mnemonic}",
                                                modifier = Modifier.padding(10.dp),
                                                style = MaterialTheme.typography.bodySmall,
                                                color = PrimaryAccent,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }

                                        Text(
                                            text = "Etymology: ${trap.etymologyAnchor}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = CyanAccent
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
            2 -> {
                // High-Yield Cheat Sheets View
                val categories = listOf("All", "Quantitative (Number Theory)", "Quantitative (Algebra)", "Quantitative (Geometry)", "Quantitative (Combinatorics & Probability)", "Verbal (Sentence Completion)", "Verbal (Critical Reasoning)", "Analytical Reasoning")

                ScrollableTabRow(
                    selectedTabIndex = categories.indexOf(uiState.selectedCheatSheetCategory).coerceAtLeast(0),
                    containerColor = Color.Transparent,
                    edgePadding = 0.dp,
                    divider = {},
                    indicator = {}
                ) {
                    categories.forEach { cat ->
                        val isSelected = uiState.selectedCheatSheetCategory == cat
                        Surface(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable { viewModel.setSelectedCheatSheetCategory(cat) },
                            color = if (isSelected) GoldPrimary else DarkCardBg,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) GoldPrimary else DarkCardBorder)
                        ) {
                            Text(
                                text = cat,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) Color(0xFF0F0E17) else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val filteredSheets = remember(uiState.selectedCheatSheetCategory) {
                    if (uiState.selectedCheatSheetCategory == "All") cheatSheets
                    else cheatSheets.filter { it.category == uiState.selectedCheatSheetCategory }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredSheets) { item ->
                        CheatSheetCard(item = item)
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun VocabItemCard(
    word: VocabWord,
    boxNumber: Int,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = word.phonetics,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0x33D4AF37),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Box $boxNumber",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldPrimary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Text(
                text = "${word.partOfSpeech} • ${word.clusterName}",
                style = MaterialTheme.typography.labelSmall,
                color = CyanAccent
            )

            Text(
                text = word.definition,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Sample Sentence:\n\"${word.sampleSentence}\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Etymology: ${word.etymology}",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryAccent
                    )

                    if (word.synonyms.isNotEmpty()) {
                        Text(
                            text = "Synonyms: " + word.synonyms.joinToString(", "),
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldPass
                        )
                    }

                    if (word.antonyms.isNotEmpty()) {
                        Text(
                            text = "Antonyms: " + word.antonyms.joinToString(", "),
                            style = MaterialTheme.typography.labelSmall,
                            color = RoseAlert
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CheatSheetCard(item: CheatSheetItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        border = androidx.compose.foundation.BorderStroke(1.dp, DarkCardBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                color = GoldPrimary.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = item.category,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Formula Box
            Surface(
                color = IndigoSurface,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.formulaOrConcept,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary
                )
            }

            Text(
                text = item.explanation,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Sample Application
            Surface(
                color = Color(0x1A00D2FF),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Real GAT Application:",
                        style = MaterialTheme.typography.labelSmall,
                        color = CyanAccent,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.sampleApplication,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Pro Tips
            if (item.tips.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "⚡ Pro Tips & Traps:",
                        style = MaterialTheme.typography.labelSmall,
                        color = EmeraldPass,
                        fontWeight = FontWeight.Bold
                    )
                    item.tips.forEach { tip ->
                        Text(
                            text = "• $tip",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

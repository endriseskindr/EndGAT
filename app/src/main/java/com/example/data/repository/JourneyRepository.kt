package com.example.data.repository

import com.example.data.models.JourneyLevel

object JourneyRepository {

    val allLevels: List<JourneyLevel> by lazy {
        val totalLevels = 80
        val questions = QuestionsRepository.allQuestions

        (1..totalLevels).map { levelNum ->
            val isBoss = (levelNum % 10 == 0)
            val chapterId = ((levelNum - 1) % 11) + 1
            val tier = when {
                levelNum <= 20 -> "Foundation Tier"
                levelNum <= 40 -> "Intermediate Mastery"
                levelNum <= 60 -> "Advanced Analytical"
                else -> "Grandmaster Simulation"
            }

            val title = when (levelNum) {
                1 -> "Diagnostic Primer"
                2 -> "Arithmetic Foundations"
                3 -> "Number Properties"
                4 -> "Linear Systems"
                5 -> "Sentence Mechanics"
                6 -> "Geometry Essentials"
                7 -> "Reading Inferences"
                8 -> "Probability Basics"
                9 -> "Analogies & Antonyms"
                10 -> "BOSS: Foundation Gatekeeper"
                15 -> "Quadratic Crucible"
                20 -> "BOSS: Quantitative Sprint"
                30 -> "BOSS: Verbal Reasoning Mastery"
                40 -> "BOSS: Analytical Deduction Trial"
                50 -> "BOSS: Advanced Hybrid Challenge"
                60 -> "BOSS: Speed Blitz Gauntlet"
                70 -> "BOSS: GAT Trap Decoy Labyrinth"
                80 -> "FINAL BOSS: Grandmaster GAT Simulation"
                else -> {
                    val baseTitles = listOf(
                        "Lexical Precision", "Modular Reasoning", "Triangles & Coordinate Planes",
                        "Data Sufficiency Drill", "Contrast Pivot Analysis", "Argument Assumption Hunt",
                        "High-Yield Affinity Maps", "Combinatorial Grid", "Permutation Mastery",
                        "Syllogistic Deductions", "Decoy Elimination Trial", "Timed Pressure Sprint"
                    )
                    baseTitles[(levelNum - 1) % baseTitles.size]
                }
            }

            val startIndex = (levelNum - 1) * 10
            val questionSlice = (0 until 10).map { offset ->
                val qIdx = (startIndex + offset) % questions.size
                questions[qIdx].id
            }

            val desc = if (isBoss) {
                "Major milestone examination with mixed high-difficulty questions and timed constraints. Pass with >= 70% to unlock next tier."
            } else {
                "Mastery challenge covering core Chapter $chapterId concepts, targeted traps, and accuracy drills."
            }

            JourneyLevel(
                levelNumber = levelNum,
                title = title,
                chapterId = chapterId,
                description = desc,
                questionIds = questionSlice,
                passingScorePercent = if (isBoss) 80 else 70,
                xpReward = if (isBoss) 300 else 100,
                isBossLevel = isBoss,
                tierTitle = tier
            )
        }
    }

    fun getLevel(levelNumber: Int): JourneyLevel? {
        return allLevels.find { it.levelNumber == levelNumber }
    }
}

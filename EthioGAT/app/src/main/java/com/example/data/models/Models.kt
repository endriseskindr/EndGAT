package com.example.data.models

enum class ChapterType(val id: Int, val title: String, val subtitle: String, val category: String) {
    ARITHMETIC_NUMBER_THEORY(1, "Arithmetic & Number Theory", "Primes, divisibility, percentages, ratios & fractions", "Quantitative"),
    ALGEBRA_FUNCTIONS(2, "Algebra & Functions", "Linear, quadratics, inequalities & system of equations", "Quantitative"),
    GEOMETRY_MEASUREMENT(3, "Geometry & Measurement", "Angles, triangles, circles, coordinate plane & 3D shapes", "Quantitative"),
    DATA_STATISTICS_PROBABILITY(4, "Data, Statistics & Probability", "Mean/median/mode, permutations, combinations & graphs", "Quantitative"),
    READING_COMPREHENSION(5, "Reading Comprehension", "Main idea, structural flow, tone & implicit deduction", "Verbal"),
    SENTENCE_COMPLETION_EQUIVALENCE(6, "Sentence Equivalence & Completion", "Context clues, double blanks & pivot transitions", "Verbal"),
    CRITICAL_REASONING(7, "Critical Reasoning & Argumentation", "Assumptions, strengtheners, weakeners & logical fallacies", "Verbal"),
    SYNONYMS_ANTONYMS_RELATIONSHIPS(8, "Synonyms, Antonyms & Analogies", "Semantic degrees, word pairs & high-yield affinities", "Verbal"),
    ANALYTICAL_LOGICAL_DEDUCTION(9, "Analytical & Logical Deduction", "Seating arrangements, conditional logic & sequence puzzles", "Analytical"),
    GAT_TRAP_MASTERY(10, "GAT Trap & Decoy Mastery", "Decoy traps, extreme wording, reverse causality & scope shifts", "Strategy"),
    FULL_EXAM_SIMULATION(11, "Comprehensive Timed Simulation", "High-yield diagnostic test sets under real exam constraints", "Simulation");

    companion object {
        fun fromId(id: Int): ChapterType = values().find { it.id == id } ?: ARITHMETIC_NUMBER_THEORY
    }
}

enum class Difficulty {
    EASY, MEDIUM, HARD, EXAM_LEVEL
}

data class Question(
    val id: String,
    val chapterId: Int,
    val subtopic: String,
    val prompt: String,
    val passage: String? = null,
    val options: List<String>,
    val correctOptionIndex: Int,
    val solutionExplanation: String,
    val trapWarning: String? = null,
    val formulaOrRule: String? = null,
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val xpValue: Int = 20
)

enum class VocabTier(val label: String, val level: Int) {
    ESSENTIAL("Tier 1 - High Frequency", 1),
    HIGH_YIELD("Tier 2 - Advanced", 2),
    EXPERT("Tier 3 - Master", 3)
}

data class VocabWord(
    val id: String,
    val word: String,
    val phonetics: String,
    val partOfSpeech: String,
    val definition: String,
    val sampleSentence: String,
    val etymology: String,
    val synonyms: List<String>,
    val antonyms: List<String>,
    val clusterId: Int,
    val clusterName: String,
    val category: String,
    val tier: VocabTier = VocabTier.HIGH_YIELD
)

data class VocabCluster(
    val id: Int,
    val name: String,
    val category: String,
    val tier: VocabTier,
    val description: String,
    val keyWordsCount: Int
)

data class GatTrapWord(
    val id: Int,
    val word: String,
    val trapType: String,
    val whyDeceptive: String,
    val commonMisconception: String,
    val realDefinition: String,
    val etymologyAnchor: String,
    val sampleGatSentence: String,
    val decoyOption: String,
    val correctOption: String,
    val deepExplanation: String,
    val mnemonic: String
)

data class JourneyLevel(
    val levelNumber: Int,
    val title: String,
    val chapterId: Int,
    val description: String,
    val questionIds: List<String>,
    val passingScorePercent: Int = 70,
    val xpReward: Int = 100,
    val isBossLevel: Boolean = false,
    val tierTitle: String = "Foundation"
)

enum class QuizMode {
    LEVEL_JOURNEY,
    CHAPTER_DRILL,
    FULL_MOCK_EXAM,
    SPEED_RUN_60S,
    ERROR_NOTEBOOK_RETRY,
    CUSTOM_QUIZ,
    TRAP_DRILL
}

data class QuizSession(
    val mode: QuizMode,
    val title: String,
    val questions: List<Question>,
    val currentQuestionIndex: Int = 0,
    val userAnswers: Map<Int, Int> = emptyMap(),
    val flags: Set<Int> = emptySet(),
    val timeRemainingSeconds: Int? = null,
    val elapsedTimeSeconds: Int = 0,
    val isSubmitted: Boolean = false,
    val levelNumber: Int? = null
) {
    val totalQuestions: Int get() = questions.size
    val answeredCount: Int get() = userAnswers.size
    val currentQuestion: Question? get() = questions.getOrNull(currentQuestionIndex)
    
    val correctCount: Int get() = userAnswers.count { (index, chosen) ->
        questions.getOrNull(index)?.correctOptionIndex == chosen
    }
    
    val scorePercentage: Int get() = if (totalQuestions == 0) 0 else ((correctCount * 100) / totalQuestions)
    val xpEarned: Int get() = correctCount * 25 + if (scorePercentage >= 80) 50 else 0
}

data class CheatSheetItem(
    val id: String,
    val category: String,
    val title: String,
    val formulaOrConcept: String,
    val explanation: String,
    val sampleApplication: String,
    val tips: List<String>
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean,
    val currentProgress: Int,
    val targetProgress: Int
)

data class ExamReadinessData(
    val overallScore: Int, // 0 to 100
    val quantScore: Int,
    val verbalScore: Int,
    val analyticalScore: Int,
    val totalQuestionsAttempted: Int,
    val accuracyRate: Float,
    val levelsCompleted: Int,
    val vocabMastered: Int,
    val trapsMastered: Int,
    val currentStreak: Int,
    val estimatedGatPercentile: Int
)

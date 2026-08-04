package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.entities.LevelProgressEntity
import com.example.data.local.entities.QuestionAttemptEntity
import com.example.data.local.entities.TrapMasteryEntity
import com.example.data.local.entities.UserStatsEntity
import com.example.data.local.entities.VocabMasteryEntity
import com.example.data.models.ChapterType
import com.example.data.models.CheatSheetItem
import com.example.data.models.ExamReadinessData
import com.example.data.models.GatTrapWord
import com.example.data.models.JourneyLevel
import com.example.data.models.Question
import com.example.data.models.QuizMode
import com.example.data.models.QuizSession
import com.example.data.models.VocabCluster
import com.example.data.models.VocabTier
import com.example.data.models.VocabWord
import com.example.data.repository.CheatSheetsData
import com.example.data.repository.GatTrapsData
import com.example.data.repository.JourneyRepository
import com.example.data.repository.QuestionsRepository
import com.example.data.repository.VocabClustersData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class GatUiState(
    val currentTab: Int = 0, // 0: Home, 1: Practice, 2: Review, 3: Library, 4: Progress
    val activeQuiz: QuizSession? = null,
    val selectedVocabCluster: Int? = null,
    val vocabSearchQuery: String = "",
    val vocabTierFilter: VocabTier? = null,
    val selectedTrapId: Int? = null,
    val trapSearchQuery: String = "",
    val activeFlashcardIndex: Int = 0,
    val isFlashcardFlipped: Boolean = false,
    val selectedCheatSheetCategory: String = "All",
    val selectedLibrarySubTab: Int = 0 // 0: Vocab, 1: Traps, 2: Cheat Sheets
)

class GatViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.gatDao()

    private val _uiState = MutableStateFlow(GatUiState())
    val uiState: StateFlow<GatUiState> = _uiState.asStateFlow()

    // Level Progress Flow
    val levelProgressList: StateFlow<List<LevelProgressEntity>> = dao.getAllLevelProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Attempts Flow
    val allAttempts: StateFlow<List<QuestionAttemptEntity>> = dao.getAllAttempts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val missedQuestionIds: StateFlow<List<String>> = dao.getMissedQuestionIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Vocab Mastery Flow
    val vocabMasteryList: StateFlow<List<VocabMasteryEntity>> = dao.getAllVocabMastery()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Trap Mastery Flow
    val trapMasteryList: StateFlow<List<TrapMasteryEntity>> = dao.getAllTrapMastery()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // User Stats Flow
    val userStats: StateFlow<UserStatsEntity?> = dao.getUserStatsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Derived Exam Readiness Metric
    val examReadiness: StateFlow<ExamReadinessData> = combine(
        levelProgressList,
        allAttempts,
        vocabMasteryList,
        trapMasteryList,
        userStats
    ) { levels, attempts, vocab, traps, stats ->
        val levelsDone = levels.count { it.stars > 0 }
        val totalAttempts = attempts.size
        val correctAttempts = attempts.count { it.isCorrect }
        val accuracy = if (totalAttempts == 0) 0.72f else (correctAttempts.toFloat() / totalAttempts.toFloat())

        val masteredVocab = vocab.count { it.leitnerBox >= 3 }
        val masteredTraps = traps.count { it.isMastered }

        val quantAttempts = attempts.filter { att ->
            val q = QuestionsRepository.getQuestionById(att.questionId)
            q != null && q.chapterId in 1..4
        }
        val verbalAttempts = attempts.filter { att ->
            val q = QuestionsRepository.getQuestionById(att.questionId)
            q != null && q.chapterId in 5..8
        }
        val analyticalAttempts = attempts.filter { att ->
            val q = QuestionsRepository.getQuestionById(att.questionId)
            q != null && (q.chapterId == 9 || q.chapterId == 10)
        }

        fun calcSubscore(subset: List<QuestionAttemptEntity>, defaultScore: Int): Int {
            if (subset.isEmpty()) return defaultScore
            val acc = subset.count { it.isCorrect }.toFloat() / subset.size.toFloat()
            return (acc * 100).toInt().coerceIn(30, 99)
        }

        val quantScore = calcSubscore(quantAttempts, 74)
        val verbalScore = calcSubscore(verbalAttempts, 78)
        val analyticalScore = calcSubscore(analyticalAttempts, 71)

        val overall = ((quantScore * 0.4f) + (verbalScore * 0.4f) + (analyticalScore * 0.2f)).toInt()
            .coerceIn(40, 99)

        val percentile = (overall + (levelsDone / 4)).coerceIn(50, 99)

        ExamReadinessData(
            overallScore = overall,
            quantScore = quantScore,
            verbalScore = verbalScore,
            analyticalScore = analyticalScore,
            totalQuestionsAttempted = totalAttempts,
            accuracyRate = accuracy,
            levelsCompleted = levelsDone,
            vocabMastered = masteredVocab,
            trapsMastered = masteredTraps,
            currentStreak = stats?.streakDays ?: 1,
            estimatedGatPercentile = percentile
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ExamReadinessData(
            overallScore = 75,
            quantScore = 74,
            verbalScore = 78,
            analyticalScore = 71,
            totalQuestionsAttempted = 0,
            accuracyRate = 0.75f,
            levelsCompleted = 0,
            vocabMastered = 0,
            trapsMastered = 0,
            currentStreak = 1,
            estimatedGatPercentile = 78
        )
    )

    init {
        initializeDataIfEmpty()
    }

    private fun initializeDataIfEmpty() {
        viewModelScope.launch {
            val stats = dao.getUserStats()
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            if (stats == null) {
                dao.insertOrUpdateStats(
                    UserStatsEntity(
                        id = 1,
                        streakDays = 3,
                        totalXp = 450,
                        dailyGoalQuestions = 20,
                        dailyGoalDone = 8,
                        dailyGoalDate = todayStr
                    )
                )
            }

            val existingLevels = dao.getLevelProgress(1)
            if (existingLevels == null) {
                val initialLevels = JourneyRepository.allLevels.mapIndexed { idx, level ->
                    LevelProgressEntity(
                        levelNumber = level.levelNumber,
                        stars = if (idx == 0) 3 else if (idx == 1) 2 else 0,
                        highScorePercent = if (idx == 0) 100 else if (idx == 1) 80 else 0,
                        isUnlocked = idx <= 2,
                        completedAt = if (idx < 2) System.currentTimeMillis() else 0L
                    )
                }
                dao.insertAllLevels(initialLevels)
            }
        }
    }

    fun setTab(index: Int) {
        _uiState.value = _uiState.value.copy(currentTab = index)
    }

    fun setLibrarySubTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedLibrarySubTab = index)
    }

    fun setVocabSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(vocabSearchQuery = query)
    }

    fun setVocabClusterFilter(clusterId: Int?) {
        _uiState.value = _uiState.value.copy(selectedVocabCluster = clusterId)
    }

    fun setVocabTierFilter(tier: VocabTier?) {
        _uiState.value = _uiState.value.copy(vocabTierFilter = tier)
    }

    fun setTrapSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(trapSearchQuery = query)
    }

    fun setSelectedCheatSheetCategory(category: String) {
        _uiState.value = _uiState.value.copy(selectedCheatSheetCategory = category)
    }

    fun flipFlashcard() {
        _uiState.value = _uiState.value.copy(isFlashcardFlipped = !_uiState.value.isFlashcardFlipped)
    }

    fun nextFlashcard(totalCards: Int) {
        if (totalCards > 0) {
            val next = (_uiState.value.activeFlashcardIndex + 1) % totalCards
            _uiState.value = _uiState.value.copy(activeFlashcardIndex = next, isFlashcardFlipped = false)
        }
    }

    fun previousFlashcard(totalCards: Int) {
        if (totalCards > 0) {
            val prev = if (_uiState.value.activeFlashcardIndex - 1 < 0) totalCards - 1 else _uiState.value.activeFlashcardIndex - 1
            _uiState.value = _uiState.value.copy(activeFlashcardIndex = prev, isFlashcardFlipped = false)
        }
    }

    // Quiz Session Launchers
    fun startLevelQuiz(levelNumber: Int) {
        val level = JourneyRepository.getLevel(levelNumber) ?: return
        val questions = level.questionIds.mapNotNull { QuestionsRepository.getQuestionById(it) }
        val session = QuizSession(
            mode = QuizMode.LEVEL_JOURNEY,
            title = "Level $levelNumber: ${level.title}",
            questions = questions,
            levelNumber = levelNumber,
            timeRemainingSeconds = if (level.isBossLevel) 600 else 900
        )
        _uiState.value = _uiState.value.copy(activeQuiz = session)
    }

    fun startChapterDrill(chapterId: Int) {
        val chapter = ChapterType.fromId(chapterId)
        val questions = QuestionsRepository.getQuestionsForChapter(chapterId).shuffled().take(15)
        val session = QuizSession(
            mode = QuizMode.CHAPTER_DRILL,
            title = "Drill: ${chapter.title}",
            questions = questions,
            timeRemainingSeconds = 900
        )
        _uiState.value = _uiState.value.copy(activeQuiz = session)
    }

    fun startFullMockExam() {
        val quant = (1..4).flatMap { QuestionsRepository.getQuestionsForChapter(it).shuffled().take(5) }
        val verbal = (5..8).flatMap { QuestionsRepository.getQuestionsForChapter(it).shuffled().take(5) }
        val analytical = (9..10).flatMap { QuestionsRepository.getQuestionsForChapter(it).shuffled().take(5) }
        val simulation = QuestionsRepository.getQuestionsForChapter(11).shuffled().take(5)

        val fullSet = (quant + verbal + analytical + simulation).shuffled()
        val session = QuizSession(
            mode = QuizMode.FULL_MOCK_EXAM,
            title = "Grandmaster Timed GAT Simulation (40 Questions)",
            questions = fullSet,
            timeRemainingSeconds = 2400
        )
        _uiState.value = _uiState.value.copy(activeQuiz = session)
    }

    fun startSpeedRun() {
        val speedSet = QuestionsRepository.allQuestions.shuffled().take(10)
        val session = QuizSession(
            mode = QuizMode.SPEED_RUN_60S,
            title = "60-Second Rapid Sprint Gauntlet",
            questions = speedSet,
            timeRemainingSeconds = 60
        )
        _uiState.value = _uiState.value.copy(activeQuiz = session)
    }

    fun startTrapDrill() {
        val trapQuestions = QuestionsRepository.getQuestionsForChapter(10).shuffled().take(15)
        val session = QuizSession(
            mode = QuizMode.TRAP_DRILL,
            title = "25 GAT Decoy Trap Mastery Drill",
            questions = trapQuestions,
            timeRemainingSeconds = 600
        )
        _uiState.value = _uiState.value.copy(activeQuiz = session)
    }

    fun startErrorNotebookRetry() {
        viewModelScope.launch {
            val missed = dao.getMissedQuestionIds()
            // We can fetch from current state or directly
            val missedIds = missedQuestionIds.value
            val questions = missedIds.mapNotNull { QuestionsRepository.getQuestionById(it) }.take(20)
            if (questions.isNotEmpty()) {
                val session = QuizSession(
                    mode = QuizMode.ERROR_NOTEBOOK_RETRY,
                    title = "Error Notebook Redemption Drill",
                    questions = questions,
                    timeRemainingSeconds = null
                )
                _uiState.value = _uiState.value.copy(activeQuiz = session)
            }
        }
    }

    fun answerCurrentQuestion(chosenOptionIndex: Int) {
        val quiz = _uiState.value.activeQuiz ?: return
        val newAnswers = quiz.userAnswers.toMutableMap()
        newAnswers[quiz.currentQuestionIndex] = chosenOptionIndex
        _uiState.value = _uiState.value.copy(
            activeQuiz = quiz.copy(userAnswers = newAnswers)
        )
    }

    fun toggleFlagCurrentQuestion() {
        val quiz = _uiState.value.activeQuiz ?: return
        val currentIdx = quiz.currentQuestionIndex
        val newFlags = quiz.flags.toMutableSet()
        if (newFlags.contains(currentIdx)) newFlags.remove(currentIdx) else newFlags.add(currentIdx)
        _uiState.value = _uiState.value.copy(
            activeQuiz = quiz.copy(flags = newFlags)
        )
    }

    fun navigateQuizQuestion(index: Int) {
        val quiz = _uiState.value.activeQuiz ?: return
        if (index in 0 until quiz.totalQuestions) {
            _uiState.value = _uiState.value.copy(
                activeQuiz = quiz.copy(currentQuestionIndex = index)
            )
        }
    }

    fun submitQuiz() {
        val quiz = _uiState.value.activeQuiz ?: return
        val submittedQuiz = quiz.copy(isSubmitted = true)
        _uiState.value = _uiState.value.copy(activeQuiz = submittedQuiz)

        viewModelScope.launch {
            // Record attempts in database
            quiz.userAnswers.forEach { (index, chosen) ->
                val q = quiz.questions.getOrNull(index) ?: return@forEach
                val isCorrect = q.correctOptionIndex == chosen
                dao.insertAttempt(
                    QuestionAttemptEntity(
                        questionId = q.id,
                        userChoice = chosen,
                        isCorrect = isCorrect,
                        timestamp = System.currentTimeMillis()
                    )
                )
                if (isCorrect) {
                    dao.clearMissedQuestion(q.id)
                }
            }

            // Update level progress if Level Quiz
            if (quiz.mode == QuizMode.LEVEL_JOURNEY && quiz.levelNumber != null) {
                val levelNum = quiz.levelNumber
                val scorePct = quiz.scorePercentage
                val stars = when {
                    scorePct >= 90 -> 3
                    scorePct >= 75 -> 2
                    scorePct >= 60 -> 1
                    else -> 0
                }
                val existing = dao.getLevelProgress(levelNum)
                val newHighScore = maxOf(scorePct, existing?.highScorePercent ?: 0)
                val newStars = maxOf(stars, existing?.stars ?: 0)
                dao.insertOrUpdateLevelProgress(
                    LevelProgressEntity(
                        levelNumber = levelNum,
                        stars = newStars,
                        highScorePercent = newHighScore,
                        isUnlocked = true,
                        completedAt = System.currentTimeMillis()
                    )
                )

                // Unlock next level if passed
                if (scorePct >= 60 && levelNum < 80) {
                    val nextLevelNum = levelNum + 1
                    val nextExisting = dao.getLevelProgress(nextLevelNum)
                    dao.insertOrUpdateLevelProgress(
                        LevelProgressEntity(
                            levelNumber = nextLevelNum,
                            stars = nextExisting?.stars ?: 0,
                            highScorePercent = nextExisting?.highScorePercent ?: 0,
                            isUnlocked = true,
                            completedAt = nextExisting?.completedAt ?: 0L
                        )
                    )
                }
            }

            // Update user stats
            val currentStats = dao.getUserStats() ?: UserStatsEntity()
            val addedXp = quiz.xpEarned
            val newDailyDone = currentStats.dailyGoalDone + quiz.answeredCount
            dao.insertOrUpdateStats(
                currentStats.copy(
                    totalXp = currentStats.totalXp + addedXp,
                    dailyGoalDone = newDailyDone,
                    lastActiveDate = System.currentTimeMillis()
                )
            )
        }
    }

    fun exitQuiz() {
        _uiState.value = _uiState.value.copy(activeQuiz = null)
    }

    fun rateVocabMastery(wordId: String, qualityScore: Int) {
        // Spaced Repetition update (Leitner 5-box algorithm)
        viewModelScope.launch {
            val existing = dao.getVocabMastery(wordId)
            val currentBox = existing?.leitnerBox ?: 1
            val newBox = if (qualityScore >= 3) {
                (currentBox + 1).coerceAtMost(5)
            } else {
                1
            }
            val intervalDays = when (newBox) {
                1 -> 1
                2 -> 3
                3 -> 7
                4 -> 14
                else -> 30
            }
            val due = System.currentTimeMillis() + (intervalDays * 24 * 60 * 60 * 1000L)
            dao.insertOrUpdateVocabMastery(
                VocabMasteryEntity(
                    wordId = wordId,
                    leitnerBox = newBox,
                    intervalDays = intervalDays,
                    repetitions = (existing?.repetitions ?: 0) + 1,
                    dueDate = due,
                    isBookmarked = existing?.isBookmarked ?: false,
                    lastReviewed = System.currentTimeMillis()
                )
            )
        }
    }

    fun toggleVocabBookmark(wordId: String, isBookmarked: Boolean) {
        viewModelScope.launch {
            val existing = dao.getVocabMastery(wordId)
            if (existing == null) {
                dao.insertOrUpdateVocabMastery(
                    VocabMasteryEntity(wordId = wordId, isBookmarked = isBookmarked)
                )
            } else {
                dao.toggleVocabBookmark(wordId, isBookmarked)
            }
        }
    }

    fun toggleTrapMastery(trapId: Int, isMastered: Boolean) {
        viewModelScope.launch {
            val existing = dao.getTrapMastery(trapId)
            dao.insertOrUpdateTrapMastery(
                TrapMasteryEntity(
                    trapId = trapId,
                    isMastered = isMastered,
                    reviewCount = (existing?.reviewCount ?: 0) + 1,
                    isBookmarked = existing?.isBookmarked ?: false,
                    lastReviewed = System.currentTimeMillis()
                )
            )
        }
    }

    fun toggleTrapBookmark(trapId: Int, isBookmarked: Boolean) {
        viewModelScope.launch {
            val existing = dao.getTrapMastery(trapId)
            if (existing == null) {
                dao.insertOrUpdateTrapMastery(
                    TrapMasteryEntity(trapId = trapId, isBookmarked = isBookmarked)
                )
            } else {
                dao.toggleTrapBookmark(trapId, isBookmarked)
            }
        }
    }
}

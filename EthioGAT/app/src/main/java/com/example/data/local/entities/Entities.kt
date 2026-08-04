package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_progress")
data class LevelProgressEntity(
    @PrimaryKey val levelNumber: Int,
    val stars: Int = 0,
    val highScorePercent: Int = 0,
    val isUnlocked: Boolean = false,
    val completedAt: Long = 0L
)

@Entity(tableName = "question_attempts")
data class QuestionAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionId: String,
    val userChoice: Int,
    val isCorrect: Boolean,
    val timeSpentSec: Int = 0,
    val errorTag: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "vocab_mastery")
data class VocabMasteryEntity(
    @PrimaryKey val wordId: String,
    val leitnerBox: Int = 1,
    val easeFactor: Float = 2.5f,
    val intervalDays: Int = 1,
    val repetitions: Int = 0,
    val dueDate: Long = System.currentTimeMillis(),
    val isBookmarked: Boolean = false,
    val lastReviewed: Long = 0L
)

@Entity(tableName = "trap_mastery")
data class TrapMasteryEntity(
    @PrimaryKey val trapId: Int,
    val isMastered: Boolean = false,
    val reviewCount: Int = 0,
    val isBookmarked: Boolean = false,
    val lastReviewed: Long = 0L
)

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey val id: Int = 1,
    val streakDays: Int = 1,
    val lastActiveDate: Long = System.currentTimeMillis(),
    val totalXp: Int = 0,
    val dailyGoalQuestions: Int = 20,
    val dailyGoalDone: Int = 0,
    val dailyGoalDate: String = ""
)

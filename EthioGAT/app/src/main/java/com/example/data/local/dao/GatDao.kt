package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entities.LevelProgressEntity
import com.example.data.local.entities.QuestionAttemptEntity
import com.example.data.local.entities.TrapMasteryEntity
import com.example.data.local.entities.UserStatsEntity
import com.example.data.local.entities.VocabMasteryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GatDao {
    // Level Progress
    @Query("SELECT * FROM level_progress ORDER BY levelNumber ASC")
    fun getAllLevelProgress(): Flow<List<LevelProgressEntity>>

    @Query("SELECT * FROM level_progress WHERE levelNumber = :levelNumber")
    suspend fun getLevelProgress(levelNumber: Int): LevelProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateLevelProgress(progress: LevelProgressEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllLevels(levels: List<LevelProgressEntity>)

    // Question Attempts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: QuestionAttemptEntity)

    @Query("SELECT * FROM question_attempts ORDER BY timestamp DESC")
    fun getAllAttempts(): Flow<List<QuestionAttemptEntity>>

    @Query("SELECT * FROM question_attempts WHERE isCorrect = 0 ORDER BY timestamp DESC")
    fun getIncorrectAttempts(): Flow<List<QuestionAttemptEntity>>

    @Query("SELECT DISTINCT questionId FROM question_attempts WHERE isCorrect = 0")
    fun getMissedQuestionIds(): Flow<List<String>>

    @Query("DELETE FROM question_attempts WHERE questionId = :questionId AND isCorrect = 0")
    suspend fun clearMissedQuestion(questionId: String)

    // Vocab Mastery (Spaced Repetition)
    @Query("SELECT * FROM vocab_mastery")
    fun getAllVocabMastery(): Flow<List<VocabMasteryEntity>>

    @Query("SELECT * FROM vocab_mastery WHERE wordId = :wordId")
    suspend fun getVocabMastery(wordId: String): VocabMasteryEntity?

    @Query("SELECT * FROM vocab_mastery WHERE isBookmarked = 1")
    fun getBookmarkedVocab(): Flow<List<VocabMasteryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateVocabMastery(mastery: VocabMasteryEntity)

    @Query("UPDATE vocab_mastery SET isBookmarked = :isBookmarked WHERE wordId = :wordId")
    suspend fun toggleVocabBookmark(wordId: String, isBookmarked: Boolean)

    // Trap Mastery
    @Query("SELECT * FROM trap_mastery")
    fun getAllTrapMastery(): Flow<List<TrapMasteryEntity>>

    @Query("SELECT * FROM trap_mastery WHERE trapId = :trapId")
    suspend fun getTrapMastery(trapId: Int): TrapMasteryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateTrapMastery(mastery: TrapMasteryEntity)

    @Query("UPDATE trap_mastery SET isBookmarked = :isBookmarked WHERE trapId = :trapId")
    suspend fun toggleTrapBookmark(trapId: Int, isBookmarked: Boolean)

    // User Stats
    @Query("SELECT * FROM user_stats WHERE id = 1")
    fun getUserStatsFlow(): Flow<UserStatsEntity?>

    @Query("SELECT * FROM user_stats WHERE id = 1")
    suspend fun getUserStats(): UserStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateStats(stats: UserStatsEntity)
}

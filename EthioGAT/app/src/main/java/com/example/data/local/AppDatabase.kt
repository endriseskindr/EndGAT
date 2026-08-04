package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.GatDao
import com.example.data.local.entities.LevelProgressEntity
import com.example.data.local.entities.QuestionAttemptEntity
import com.example.data.local.entities.TrapMasteryEntity
import com.example.data.local.entities.UserStatsEntity
import com.example.data.local.entities.VocabMasteryEntity

@Database(
    entities = [
        LevelProgressEntity::class,
        QuestionAttemptEntity::class,
        VocabMasteryEntity::class,
        TrapMasteryEntity::class,
        UserStatsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gatDao(): GatDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gat_master_database.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

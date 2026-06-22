package com.legaldocai.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.legaldocai.models.DocumentItem
import com.legaldocai.models.QueryHistory

@Dao
interface DocumentDao {
    @Query("SELECT * FROM documents ORDER BY addedAt DESC")
    fun getAllDocuments(): LiveData<List<DocumentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: DocumentItem)

    @Delete
    suspend fun deleteDocument(document: DocumentItem)

    @Query("DELETE FROM documents")
    suspend fun deleteAll()
}

@Dao
interface QueryHistoryDao {
    @Query("SELECT * FROM query_history ORDER BY timestamp DESC LIMIT 100")
    fun getAllHistory(): LiveData<List<QueryHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: QueryHistory)

    @Query("DELETE FROM query_history")
    suspend fun clearHistory()
}

@Database(entities = [DocumentItem::class, QueryHistory::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun queryHistoryDao(): QueryHistoryDao
}

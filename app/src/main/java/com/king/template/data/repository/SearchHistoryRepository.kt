package com.king.template.data.repository

import com.king.frame.mvvmframe.data.DataRepository
import com.king.template.data.local.AppDatabase
import com.king.template.data.model.entity.SearchHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 搜索历史
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * <p>
 * <a href="https://github.com/jenly1314">Follow me</a>
 */
@Singleton
class SearchHistoryRepository @Inject constructor() : DataRepository() {

    private val database by lazy {
        getRoomDatabase(AppDatabase::class.java)
    }

    /**
     * 插入一条历史数据
     * @param searchHistory
     */
    suspend fun insert(searchHistory: SearchHistory) {
        database.searchHistoryDao.insert(searchHistory)
    }

    /**
     * 删除所有历史数据
     */
    suspend fun deleteAll() {
        database.searchHistoryDao.deleteAll()
    }

    /**
     * 获取历史数据对应的[Flow]
     *
     * @param count 获取历史记录的条数
     */
    fun getHistoryFlow(count: Int): Flow<List<SearchHistory>> {
        return database.searchHistoryDao.getHistoryFlow(count).filterNotNull()
    }
}
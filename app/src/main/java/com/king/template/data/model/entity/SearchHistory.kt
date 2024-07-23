package com.king.template.data.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 搜索历史
 *
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Entity(indices = [Index(value = ["word"], unique = true)])
data class SearchHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String,
    val timestamp: Long,
)
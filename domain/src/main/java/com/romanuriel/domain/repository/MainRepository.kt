package com.romanuriel.domain.repository

import android.util.Log
import com.romanuriel.core.firebase.GetCategory
import com.romanuriel.core.room.AppDatabase
import com.romanuriel.core.room.entity.Category
import com.romanuriel.utils.TypeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val db: AppDatabase,
    private val category: GetCategory
) {

    suspend fun initDatabase() {
        withContext(Dispatchers.IO) {
            category.invoke()?.forEach { it ->
                val category = Category(
                    id = it["id"] as Long,
                    name = it["name"] as String
                )
                db.categoryDao().insert(category)
            }
        }
    }
}
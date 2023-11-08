package com.romanuriel.domain.repository

import com.romanuriel.core.firebase.GetCategory
import com.romanuriel.core.room.AppDatabase
import com.romanuriel.core.room.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val db: AppDatabase,
    private val category: GetCategory
) {

    suspend fun initDatabase() {
        withContext(Dispatchers.IO) {
            if(!db.categoryDao().isNotEmpty()){
                category.invoke()?.forEach {
                    val category = Category(
                        id = it["id"] as Long,
                        name = it["name"] as String
                    )
                    db.categoryDao().insert(category)
                }
            }
        }
    }
}
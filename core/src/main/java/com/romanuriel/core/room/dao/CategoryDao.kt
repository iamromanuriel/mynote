package com.romanuriel.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.romanuriel.core.room.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CATEGORY")
    fun getAll(): List<Category>

    @Insert(onConflict = REPLACE)
    fun insert(category: Category)

}
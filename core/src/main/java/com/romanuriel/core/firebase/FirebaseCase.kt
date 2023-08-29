package com.romanuriel.core.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

enum class Collection(val value: String){
    CATEGORY("category")
}


class GetCategory @Inject constructor(
    private val db: FirebaseFirestore
){
    suspend operator fun invoke(): List<Map<String, Any>>? {
        try {
            val querySnapshot = db.collection(Collection.CATEGORY.value)
                .get()
                .await()
            val categories = mutableListOf<Map<String, Any>>()
            for (document in querySnapshot.documents) {
                val categoryData = document.data
                if (categoryData != null) {
                    categories.add(categoryData)
                }
            }
            return categories
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
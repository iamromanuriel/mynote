package com.romanuriel.core.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.romanuriel.core.Task
import com.romanuriel.core.room.model.NoteItem
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

enum class Collection(val value: String){
    CATEGORY("category"),
    NOTE("note")
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


class InsertNoteFirebase @Inject constructor(
    private val db: FirebaseFirestore
){
    suspend operator fun  invoke(noteItem: NoteItem): Task<Unit> {
        val completableDeferred = CompletableDeferred<Task<Unit>>()

        db.collection(Collection.NOTE.value)
            .add(noteItem)
            .addOnSuccessListener {
                completableDeferred.complete(Task.Success(Unit))
            }
            .addOnFailureListener { e ->
                completableDeferred.complete(Task.Error(e))
            }
        return completableDeferred.await()
    }
}
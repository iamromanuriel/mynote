package com.romanuriel.service.firebase

import android.app.IntentService
import android.content.Intent
import com.romanuriel.domain.repository.NoteRepository
import javax.inject.Inject

@Suppress("DEPRECATION")
class FirebaseCloudNote @Inject constructor(private val noteRepository: NoteRepository) :
    IntentService("Firebase cloud note") {
    override fun onHandleIntent(intent: Intent?) {
        if (intent != null) {
            noteRepository.getAllNote()
        }
    }
}
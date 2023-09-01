package com.romanuriel.domain

import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.domain.usescase.InsertNewNoteUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertNewNoteUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: NoteRepository

    private lateinit var insertNewNoteUseCase: InsertNewNoteUseCase
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        insertNewNoteUseCase = InsertNewNoteUseCase(repository)
    }

    @Test
    fun toListAllNoteToCreatedUser() = runBlocking {
        val title = "Test Note."
        val content = "This is a test note."
    }
}
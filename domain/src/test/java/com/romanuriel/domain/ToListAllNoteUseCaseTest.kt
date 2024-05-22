package com.romanuriel.domain

import com.romanuriel.core.room.model.NoteItem
import com.romanuriel.domain.repository.NoteRepository
import com.romanuriel.domain.usescase.InsertNewNoteUseCase
import com.romanuriel.domain.usescase.ToListAllNoteUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class ToListAllNoteUseCaseTest {
    @RelaxedMockK
    private lateinit var repository: NoteRepository
    private lateinit var toListAllNoteUseCase: ToListAllNoteUseCase
    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        toListAllNoteUseCase = ToListAllNoteUseCase(repository)
    }
    @Test
    fun should() = runBlocking {
        //Given
        //When


        //Then

    }
}
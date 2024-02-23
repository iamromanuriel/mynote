package com.romanuriel.domain.usescase

import android.annotation.SuppressLint
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations.openMocks

@RunWith(JUnit4::class)
class InsertNewNoteUseCaseTest{

    private lateinit var mockWebServer: MockWebServer
    @Before
    fun onBefore(){
        openMocks(this)
        mockWebServer = MockWebServer()
    }
    @After
    fun onAfter(){
        mockWebServer.shutdown()
    }
    @SuppressLint("CheckResult")
    @Test
    fun callSetNameTest(){
        val inputStream = javaClass.classLoader?.getResourceAsStream("example_response.json")?.source()?.buffer()?.readString(Charsets.UTF_8)

    }
}
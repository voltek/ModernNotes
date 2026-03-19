package com.example.modernnotes.util

import com.example.modernnotes.core.APPLICATION_JSON
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MockWebServerRule : TestWatcher() {

    val server = MockWebServer()

    lateinit var url: String
        private set


    override fun starting(description: Description?) {
        super.starting(description)
        server.start()
        url = server.url("/").toString()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        server.shutdown()
    }

    fun enqueueResponse(body: String, code: Int = 200) {
        server.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(body)
        )
    }

    fun createRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = APPLICATION_JSON.toMediaType()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
package br.com.rentakelly.utils

import androidx.test.espresso.IdlingRegistry
import br.com.rentakelly.OkHttp3IdlingResourceAndroidX
import br.com.rentakelly.api.InitializerClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule: TestWatcher() {
    val mockWebServer = MockWebServer()

    override fun starting(description: Description?) {
        super.starting(description)

        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResourceAndroidX.create("OkHttp", InitializerClient.httpClient))

        mockWebServer.start(8080)
        InitializerClient.baseUrl = mockWebServer.url("/").toString()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        mockWebServer.shutdown()
    }
}
package br.com.rentakelly.repository

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import br.com.rentakelly.api.InitializerClient
import br.com.rentakelly.loadAsFixture
import br.com.rentakelly.retryer
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer


class repositoryArrange(
    private val mockWebServer: MockWebServer,
    action: repositoryArrange.() -> Unit
) {
    init {
        action.invoke(this)
    }

    fun registerIdlingResourse() {
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                InitializerClient.httpClient
            )
        )

    }

    fun startServer() {
        mockWebServer.start(8080)
        InitializerClient.baseUrl = mockWebServer.url("/").toString()
    }

    fun shutDownServer() {
        mockWebServer.shutdown()
    }

    fun enqueueResponse(responseFileName:String) {
        mockWebServer.enqueue(
            MockResponse().setBody("resposta_sucesso.json".loadAsFixture())
        )
    }

    fun starRepositoryScreen(){
        ActivityScenario.launch(RepositoryActivity::class.java)
    }

}

class repositoryAction( action: repositoryAction.() -> Unit){
    init {
        action.invoke(this)
    }

}

class repositoryAssert( action: repositoryAssert.() -> Unit){
    init {
        action.invoke(this)
    }
    fun checkTextVisible(text:String){
        retryer {
            onView(withText(text)).check(matches(isDisplayed()))
        }

    }

}

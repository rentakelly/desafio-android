package br.com.rentakelly.pull

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import br.com.rentakelly.api.InitializerClient
import br.com.rentakelly.loadAsFixture
import br.com.rentakelly.retryer
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class pullArrange(
    private val mockWebServer: MockWebServer,
    action: pullArrange.() -> Unit
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
            MockResponse().setBody("resposta_sucesso_pull.json".loadAsFixture())
        )
    }

    fun starPullScren(){
        val bundle = bundleOf(KEY_NAME to "elasticsearch", KEY_OWNER to "elastic")
        val intent = Intent(ApplicationProvider.getApplicationContext(),
            PullsActivity::class.java).apply {
            putExtras(bundle)
        }
        ActivityScenario.launch<PullsActivity>(intent)
    }

}

class PullAction( action: PullAction.() -> Unit){
    init {
        action.invoke(this)
    }

}

class pullAssert( action: pullAssert.() -> Unit){
    init {
        action.invoke(this)
    }
    fun checkTextVisible(text:String){
        retryer {
            Espresso.onView(ViewMatchers.withText(text))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

    }

}
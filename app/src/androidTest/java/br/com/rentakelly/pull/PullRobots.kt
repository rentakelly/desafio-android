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
import br.com.rentakelly.utils.HttpStatus
import br.com.rentakelly.utils.MockWebServerRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse

class pullArrange(
    private val mockWebServerRule: MockWebServerRule,
    action: pullArrange.() -> Unit
) {
    init {
        action.invoke(this)
    }


    fun enqueueResponse(responseFileName: String) {
        mockWebServerRule.mockWebServer.enqueue(
            MockResponse().setBody(responseFileName.loadAsFixture())
        )
    }

    fun enqueueResponseError() {
        mockWebServerRule.mockWebServer.enqueue(
            MockResponse().setResponseCode(HttpStatus.STATUS_400)
        )
    }

    fun enqueueError(t: Throwable) {
        mockWebServerRule.mockWebServer.enqueue(
            MockResponse()
        )
    }

    fun starPullScren() {
        val bundle = bundleOf(KEY_NAME to "elasticsearch", KEY_OWNER to "elastic")
        val intent = Intent(
            ApplicationProvider.getApplicationContext(),
            PullsActivity::class.java
        ).apply {
            putExtras(bundle)
        }
        ActivityScenario.launch<PullsActivity>(intent)
    }

}

class PullAction(action: PullAction.() -> Unit) {
    init {
        action.invoke(this)
    }

}

class pullAssert(action: pullAssert.() -> Unit) {
    init {
        action.invoke(this)
    }

    fun checkTextVisible(text: String) {
        Espresso.onView(ViewMatchers.withText(text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}
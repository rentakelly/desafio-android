package br.com.rentakelly.repository

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import br.com.rentakelly.loadAsFixture
import br.com.rentakelly.utils.HttpStatus
import br.com.rentakelly.utils.MockWebServerRule
import okhttp3.mockwebserver.MockResponse


class repositoryArrange(
    private val mockWebServerRule: MockWebServerRule,
    action: repositoryArrange.() -> Unit
) {
    init {
        action.invoke(this)
    }

    fun enqueueResponse(responseFileName: String) {
        mockWebServerRule.mockWebServer.enqueue(
            MockResponse().setBody(responseFileName.loadAsFixture())
        )
    }

    fun enqueueError(t: Throwable) {
        mockWebServerRule.mockWebServer.enqueue(
            MockResponse()
        )
    }


    fun enqueueResponseError() {
        mockWebServerRule.mockWebServer.enqueue(
            MockResponse().setResponseCode(HttpStatus.STATUS_400)
        )
    }

    fun starRepositoryScren() {
        ActivityScenario.launch(RepositoryActivity::class.java)
    }
}

class repositoryAction(action: repositoryAction.() -> Unit) {
    init {
        action.invoke(this)
    }

}

class repositoryAssert(action: repositoryAssert.() -> Unit) {
    init {
        action.invoke(this)
    }

    fun checkTextVisible(text: String) {
        //retryer {
        onView(withText(text)).check(matches(isDisplayed()))
        //}

    }

}

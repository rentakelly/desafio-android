package br.com.rentakelly.pull

import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PullsActivityTest {

    //@get:Rule
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        pullArrange(mockWebServer) {
            registerIdlingResourse()
        }
    }


    @After
    fun teardown() {
        pullArrange(mockWebServer) {
            shutDownServer()
        }
    }

    @Test
    fun givenRequestSucessful_shouldRenderOullList() {
        pullArrange(mockWebServer) {
            enqueueResponse("resposta_sucesso_pull.json")
            startServer()
            starPullScren()
        }
        pullAssert {
            checkTextVisible("547. Friend Circles 改名为547. Number of Provinces")
        }
    }
}
package br.com.rentakelly.pull

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rentakelly.utils.MockWebServerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PullsActivityTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()


    @Test
    fun givenRequestSucessful_shouldRenderPullList() {
        pullArrange(mockWebServerRule) {
            enqueueResponse("resposta_sucesso_pull.json")
            starPullScren()
        }
        pullAssert {
            checkTextVisible("547. Friend Circles 改名为547. Number of Provinces")
        }
    }

    @Test
    fun givenRequestErrorServidor_shouldRenderPullList() {
        pullArrange(mockWebServerRule) {
            enqueueResponseError()
            starPullScren()
        }
        pullAssert {
            checkTextVisible("Erro de aplicativo")
        }
    }
    @Test
    fun givenRequestError_shouldReturnDialogAlert(){
        pullArrange(mockWebServerRule) {
            enqueueError(Throwable())
            starPullScren()
        }
        pullAssert {
            checkTextVisible("Deu merda")
        }

    }
}
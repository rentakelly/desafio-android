package br.com.rentakelly

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rentakelly.pull.pullArrange
import br.com.rentakelly.pull.pullAssert
import br.com.rentakelly.repository.repositoryArrange
import br.com.rentakelly.repository.repositoryAssert
import br.com.rentakelly.utils.MockWebServerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryActivityTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @Test
    fun givenRequestSucessful_shouldRenderRepositoriesList(){
        repositoryArrange(mockWebServerRule){
            enqueueResponse("resposta_sucesso.json")
            starRepositoryScren()
        }
        repositoryAssert{
            checkTextVisible("CS-Notes")
        }
    }
    @Test
    fun givenRequestFail_shouldRepositoriesList() {
        repositoryArrange(mockWebServerRule) {
            enqueueResponseError()
            starRepositoryScren()
        }
        repositoryAssert {
            checkTextVisible("Deu merda")
        }
    }
    @Test
    fun givenRequestError_shouldReturnDialogAlertRepository(){
        repositoryArrange(mockWebServerRule) {
            enqueueError(Throwable())
            starRepositoryScren()
        }
        repositoryAssert {
            checkTextVisible("Erro de aplicativo")
        }

    }

}


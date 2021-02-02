package br.com.rentakelly

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.rentakelly.repository.repositoryArrange
import br.com.rentakelly.repository.repositoryAssert
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryActivityTest {

    @get:Rule
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        repositoryArrange(mockWebServer){
            registerIdlingResourse()
        }
    }


    @After
    fun teardown() {
        repositoryArrange(mockWebServer){
            shutDownServer()
        }
    }

    @Test
    fun givenRequestSucessful_shouldRenderRepositoriesList(){
        repositoryArrange(mockWebServer){
            enqueueResponse("fixtures/resposta_sucesso.json")
            startServer()
            starRepositoryScreen()
        }
        repositoryAssert{
            checkTextVisible("CS-Notes")
        }
    }
    @Test
    fun givenRequestFail_shouldRepositoriesList() {
        repositoryArrange(mockWebServer) {
            enqueueResponse("")
            startServer()
            starRepositoryScreen()
        }
        repositoryAssert{
            checkTextVisible("Cleitinho")
        }
    }

}


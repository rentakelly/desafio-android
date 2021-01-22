package br.com.rentakelly.pull

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.rentakelly.FakeLogger
import br.com.rentakelly.R
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.models.Owner
import br.com.rentakelly.models.Pull
import br.com.rentakelly.models.Repo
import br.com.rentakelly.models.Repos
import br.com.rentakelly.repository.RepositoryViewModel
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException

class PullViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    var login = ""
    var name = ""
    private val fakeLogger = FakeLogger()
    private val mockWebClient = mockk<RepositoryService>()
    private val viewModel = PullViewModel(mockWebClient, fakeLogger)

    @Test
    fun givenResponseSuccessful_shouldFillResultListLiveData() {
        val resultList = createFakeReposList()
        // arrange
        every {
            mockWebClient.pullsList(login, name)
        } returns Calls.response(Response.success(resultList))
        // act
        viewModel.fetchPulls(login, name)

        // assert
        Assert.assertEquals(resultList, viewModel.liveDataPublica.value)
    }

    @Test
    fun givenReponseError_whenAPIFailure_shouldFillResultError(){
        // arrange
        every {
            mockWebClient.pullsList(login, name)
        } returns Calls.response(Response.error(404, "".toResponseBody()))

        // act
        viewModel.fetchPulls(login, name)

        //assert
        Assert.assertEquals(R.string.error_app, viewModel.listaLiveDataErroPublica.value)
    }

    @Test
    fun givenNetworkError_shouldReturnError() {
        val exceptionThrown = IOException()
        every {
            mockWebClient.pullsList(login, name)
        } returns Calls.failure(exceptionThrown)

        viewModel.fetchPulls(login, name)

        Assert.assertEquals(R.string.error_servidor, viewModel.listaLiveDataErroPublica.value)
    }

    private fun createFakeReposList(): List<Pull> {

        return (1..10).map { number ->
            Pull(
                id = number,
                title = "Repo $number",
                user = Owner( login = "joao", avatar = "hahaha" ),
                description = "Description $number",
                link = "Link $number",
                owner = Owner(avatar = "https://google.com", login = "joao")
            )
        }
    }
}
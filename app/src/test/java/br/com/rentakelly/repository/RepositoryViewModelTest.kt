package br.com.rentakelly.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.rentakelly.R
import br.com.rentakelly.utils.FakeLogger
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.models.Owner
import br.com.rentakelly.models.Repo
import br.com.rentakelly.models.Repos
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import retrofit2.mock.Calls
import java.io.IOException


class RepositoryViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeLogger = FakeLogger()
    private val mockWebClient = mockk<RepositoryService>()
    private val viewModel = RepositoryViewModel(mockWebClient, fakeLogger)

    @Test
    fun givenResponseSuccessful_shouldFillResultListLiveData() {
        val resultList = createFakeReposList()
        // arrange
        every {
            mockWebClient.reposList(any())
        } returns Calls.response(Repos(resultList))
        // act
        viewModel.loadRepos()

        // assert
        assertEquals(resultList, viewModel.liveDataPublica.value)
    }

    @Test
    fun givenReponseError_whenAPIFailure_shouldFillResultError(){
        // arrange
        every {
            mockWebClient.reposList(any())
        } returns Calls.response(Response.error(404, "".toResponseBody()))

        // act
        viewModel.loadRepos()

        //assert
        assertEquals(R.string.error_servidor, viewModel.liveDataPublicaErro.value)
    }

    @Test
    fun givenNetworkError_shouldReturnError() {
        val exceptionThrown = IOException()
        every {
            mockWebClient.reposList(any())
        } returns Calls.failure(exceptionThrown)

        viewModel.loadRepos()

        assertEquals(R.string.error_app, viewModel.liveDataPublicaErro.value)
    }

    private fun createFakeReposList(): List<Repo> {
        return (1..10).map { number ->
            Repo(
                id = number,
                name = "Repo $number",
                description = "Description $number",
                forks = number,
                stars = number * 2,
                owner = Owner(avatar = "https://google.com", login = "joao"),
                fullName = "Joao $number"
            )
        }
    }
}

package br.com.rentakelly.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rentakelly.R
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.models.Repo
import br.com.rentakelly.models.Repos
import br.com.rentakelly.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(
    private val client: RepositoryService,
    private val logger: Logger
) : ViewModel() {
    private var lista = mutableListOf<Repo>()
    private var pageNumber = 1
    private val listaLiveData: MutableLiveData<List<Repo>> = MutableLiveData()
    private val listaLiveDataErro = MutableLiveData<Int>()
    val liveDataPublica: LiveData<List<Repo>> = listaLiveData
    val liveDataPublicaErro: LiveData<Int> = listaLiveDataErro

    fun loadRepos() {

        logger.logMessege("Repository ViewModel", "$pageNumber")
        client.reposList(pageNumber).enqueue(object : Callback<Repos> {
            override fun onResponse(call: Call<Repos>, response: Response<Repos>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        lista.addAll(it.items)
                        listaLiveData.value = lista
                        pageNumber++
                    }
                } else {
                    listaLiveDataErro.postValue(R.string.error_servidor)
                }
            }

            override fun onFailure(call: Call<Repos>, t: Throwable) {
                logger.logMessege("Erro de chamada", t.message.toString())
                //Toast.makeText(RepositoryActivity(), t.message, Toast.LENGTH_LONG).show()
                listaLiveDataErro.postValue(R.string.error_app)
            }
        })
    }
}

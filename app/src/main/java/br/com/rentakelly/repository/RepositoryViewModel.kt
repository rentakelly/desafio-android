package br.com.rentakelly.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val listaLiveDataErro = MutableLiveData<Any>()
    val liveDataPublica: LiveData<List<Repo>> = listaLiveData
    val liveDataPublicaErro: LiveData<Any> = listaLiveDataErro

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
                }
            }

            override fun onFailure(call: Call<Repos>, t: Throwable) {
                logger.logMessege("Erro de chamada", t.message.toString())
                //Toast.makeText(RepositoryActivity(), t.message, Toast.LENGTH_LONG).show()
                listaLiveDataErro.postValue(t)
            }
        })
    }
}

package br.com.rentakelly.repository

import android.content.ContentProviderClient
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rentakelly.api.InitializerClient
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.models.Repo
import br.com.rentakelly.models.Repos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(private val client: RepositoryService) : ViewModel() {
    private var lista = mutableListOf<Repo>()
    private var pageNumber = 1
    private  val listaLiveData : MutableLiveData<List<Repo>> = MutableLiveData()
    val liveDataPublica : LiveData<List<Repo>> = listaLiveData

    fun loadRepos() {
        Log.d("Repository ViewModel", "$pageNumber" )
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
                Log.d("Erro de chamada", t.message.toString())
                Toast.makeText(RepositoryActivity(), t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}

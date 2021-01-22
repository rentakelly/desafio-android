package br.com.rentakelly.pull

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rentakelly.R
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.models.Pull
import br.com.rentakelly.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullViewModel(
    private val client: RepositoryService,
    private val logger: Logger
) :
    ViewModel() {

    private val listaLiveData: MutableLiveData<List<Pull>> = MutableLiveData()
    val liveDataPublica: LiveData<List<Pull>> = listaLiveData
    private val listaLiveDataErro = MutableLiveData<Int>()
    val listaLiveDataErroPublica: LiveData<Int> = listaLiveDataErro

    fun fetchPulls(login: String, name: String) {
        client.pullsList(login, name).enqueue(object : Callback<List<Pull>> {

            override fun onFailure(call: Call<List<Pull>>, t: Throwable) {
                logger.logMessege("Erro de chamada", t.message.toString())
                //Toast.makeText(PullsActivity(), t.message, Toast.LENGTH_LONG).show()
                listaLiveDataErro.postValue(R.string.error_servidor)
            }

            override fun onResponse(call: Call<List<Pull>>, response: Response<List<Pull>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listaLiveData.postValue(it)
                    }
                } else {
                    listaLiveDataErro.postValue(R.string.error_app)
                }
            }
        })
    }
}

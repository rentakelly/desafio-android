package br.com.rentakelly.pull

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rentakelly.api.RepositoryService
import br.com.rentakelly.models.Pull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullViewModel(private val client: RepositoryService) : ViewModel() {

    private  val listaLiveData : MutableLiveData<List<Pull>> = MutableLiveData()
    val liveDataPublica : LiveData<List<Pull>> = listaLiveData

    fun fetchPulls(login: String, name: String) {
        client.pullsList(login, name).enqueue(object : Callback<List<Pull>> {

            override fun onFailure(call: Call<List<Pull>>, t: Throwable) {
                Log.d("Erro de chamada", t.message.toString())
                Toast.makeText(PullsActivity(), t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Pull>>, response: Response<List<Pull>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        listaLiveData.postValue(it)
//                        binding.recyclerPull.adapter =
//                            PullAdapter(it, this@PullsActivity)
//                        pullList.addAll(it)
                        //loading()
                    }
                }
            }
        })
    }
}
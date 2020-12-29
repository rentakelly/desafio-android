package br.com.rentakelly.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.rentakelly.InitializerClient
import br.com.rentakelly.R
import br.com.rentakelly.RepositoryAdapter
import br.com.rentakelly.databinding.ActivityPullsBinding
import br.com.rentakelly.databinding.ActivityRepositoryBinding
import br.com.rentakelly.models.Pull
import br.com.rentakelly.models.Repos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val KEY_OWNER = "owner"
const val KEY_NAME = "name"

class PullsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPullsBinding
    private val client by lazy { InitializerClient.init() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPullsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {

            val login = it.getString(KEY_OWNER)
            val name = it.getString(KEY_NAME)

            if (login!=null && name!=null ) {
                fetchPulls(login, name)

            }

//            binding.tvPullname.text = "$login$name"
        }


    }

    fun fetchPulls(login: String, name: String) {
        client.pullsList(login, name).enqueue(object : Callback<List<Pull>> {

            override fun onFailure(call: Call<List<Pull>>, t: Throwable) {
                Log.d("Erro de chamada", t.message.toString())
                Toast.makeText(this@PullsActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Pull>>, response: Response<List<Pull>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
//                        binding.recyclerRepository.adapter =
//                            RepositoryAdapter(it.items, this@PullsActivity)
                    }
                }
            }

        })
    }
}
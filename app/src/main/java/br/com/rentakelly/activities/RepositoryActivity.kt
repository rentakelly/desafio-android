package br.com.rentakelly.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.rentakelly.InitializerClient.init
import br.com.rentakelly.RepositoryAdapter
import br.com.rentakelly.databinding.ActivityRepositoryBinding
import br.com.rentakelly.models.Repos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepositoryBinding

    private val client by lazy { init() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.recyclerRepository.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        client.reposList().enqueue(object : Callback<Repos>{
            override fun onResponse(call: Call<Repos>, response: Response<Repos>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        binding.recyclerRepository.adapter = RepositoryAdapter(it.items)
                    }
                }
            }

            override fun onFailure(call: Call<Repos>, t: Throwable) {
                Log.d("Erro de chamada", t.message.toString())
                Toast.makeText(this@RepositoryActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })


    }

}

//listOf(
//Repository(
//1,
//"renata",
//"rentakelly",
//Owner(
//"renta",
//"www.hahaha.com.br"),
//"lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla" +
//"lalalaalalallalalalaalalallalalalaalalallalalalaalalalla",
//1547,
//587955
//
//),Repository(
//1,
//"renata",
//"rentakelly",
//Owner(
//"renta",
//"www.hahaha.com.br"),
//"lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla" +
//"lalalaalalallalalalaalalallalalalaalalallalalalaalalalla",
//1547,
//587955
//
//),Repository(
//1,
//"renata",
//"rentakelly",
//Owner(
//"renta",
//"www.hahaha.com.br"),
//"lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla" +
//"lalalaalalallalalalaalalallalalalaalalallalalalaalalalla",
//1547,
//587955
//
//),Repository(
//1,
//"renata",
//"rentakelly",
//Owner(
//"renta",
//"www.hahaha.com.br"),
//"lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla" +
//"lalalaalalallalalalaalalallalalalaalalallalalalaalalalla",
//1547,
//587955
//
//),Repository(
//1,
//"renata",
//"rentakelly",
//Owner(
//"renta",
//"www.hahaha.com.br"),
//"lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla" +
//"lalalaalalallalalalaalalallalalalaalalallalalalaalalalla",
//1547,
//587955
//
//),Repository(
//1,
//"renata",
//"rentakelly",
//Owner(
//"renta",
//"www.hahaha.com.br"),
//"lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla lalalaalalalla" +
//"lalalaalalallalalalaalalallalalalaalalallalalalaalalalla",
//1547,
//587955
//
//)
//)
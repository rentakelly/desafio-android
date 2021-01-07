package br.com.rentakelly.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.rentakelly.InitializerClient.init
import br.com.rentakelly.RepositoryAdapter
import br.com.rentakelly.databinding.ActivityRepositoryBinding
import br.com.rentakelly.models.Repo
import br.com.rentakelly.models.Repos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val QTD_ITEMS = 20

class RepositoryActivity : AppCompatActivity(), RepositoryAdapter.RepoListener {

    private lateinit var binding: ActivityRepositoryBinding

    private var pageNumber = 1

    private val client by lazy { init() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.recyclerRepository.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        loadRepos()


    }

    private fun loadRepos() {
        client.reposList(pageNumber).enqueue(object : Callback<Repos> {
            override fun onResponse(call: Call<Repos>, response: Response<Repos>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        binding.recyclerRepository.adapter =
                            RepositoryAdapter(it.items, this@RepositoryActivity)
                    }
                }
            }

            override fun onFailure(call: Call<Repos>, t: Throwable) {
                Log.d("Erro de chamada", t.message.toString())
                Toast.makeText(this@RepositoryActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onRepoClickListener(repo: Repo) {
        Toast.makeText(this, repo.name, Toast.LENGTH_LONG).show()
        val intent = Intent(this@RepositoryActivity, PullsActivity::class.java).apply {
            putExtra(KEY_OWNER, repo.owner.login)
            putExtra(KEY_NAME, repo.name)
        }
        startActivity(intent)
    }

    override fun onThresholdReached() {
        pageNumber++
        loadRepos()
    }


}



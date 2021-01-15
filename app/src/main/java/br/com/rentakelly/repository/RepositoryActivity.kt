package br.com.rentakelly.repository

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.rentakelly.ViewModelFactory
import br.com.rentakelly.api.InitializerClient
import br.com.rentakelly.databinding.ActivityRepositoryBinding
import br.com.rentakelly.models.Repo
import br.com.rentakelly.pull.KEY_NAME
import br.com.rentakelly.pull.KEY_OWNER
import br.com.rentakelly.pull.PullsActivity

class RepositoryActivity : AppCompatActivity(), RepositoryAdapter.RepoListener {

    private val listaRepoAdapter = RepositoryAdapter( this)
    private lateinit var binding: ActivityRepositoryBinding
    private val viewModel: RepositoryViewModel by viewModels { ViewModelFactory(InitializerClient.init()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerRepository.adapter = listaRepoAdapter
        observerRepos()
        viewModel.loadRepos()
    }
    private fun observerRepos(){
        viewModel.liveDataPublica.observe(this, Observer {
            listaRepoAdapter.addRepos(it)
            listaRepoAdapter.notifyDataSetChanged()
            loading()
        })

    }
    private fun loading() {
        binding.loading.visibility = View.GONE
        binding.recyclerRepository.visibility = View.VISIBLE
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
        viewModel.loadRepos()
    }
}

package br.com.rentakelly.pull

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.rentakelly.PullViewModelFactory
import br.com.rentakelly.api.InitializerClient
import br.com.rentakelly.databinding.ActivityPullsBinding
import br.com.rentakelly.models.Pull

const val KEY_OWNER = "owner"
const val KEY_NAME = "name"

class PullsActivity : AppCompatActivity(), PullAdapter.onPullClickListener {

    private var pullList = ArrayList<Pull>()
    private var adapter = PullAdapter(pullList, this)
    private lateinit var binding: ActivityPullsBinding
    private val viewModel: PullViewModel by viewModels { PullViewModelFactory(InitializerClient.init()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPullsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarPull)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.recyclerPull.adapter = adapter
        binding.recyclerPull.layoutManager = LinearLayoutManager(this)
        binding.recyclerPull.setHasFixedSize(true)
        intent.extras?.let {
            val login = it.getString(KEY_OWNER)
            val name = it.getString(KEY_NAME)

            binding.toolbarPull.title = name

            if (login != null && name != null) {
                viewModel.fetchPulls(login, name)
            }
        }
        observerPull()
    }

    private fun observerPull(){
        viewModel.liveDataPublica.observe(this, Observer {
            adapter.pull = it
            adapter.notifyDataSetChanged()
            loading()
        })

    }

    private fun loading() {
        binding.loading.visibility = View.GONE
        binding.recyclerPull.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return false
    }

    override fun onPullClickListener(position: Int) {
        val link = adapter.pull[position].link
        val intentPull = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intentPull)
    }
}
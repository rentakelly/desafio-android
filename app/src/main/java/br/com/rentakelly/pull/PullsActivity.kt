package br.com.rentakelly.pull

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.rentakelly.api.InitializerClient
import br.com.rentakelly.databinding.ActivityPullsBinding
import br.com.rentakelly.models.Pull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val KEY_OWNER = "owner"
const val KEY_NAME = "name"

class PullsActivity : AppCompatActivity(), PullAdapter.onPullClickListener {

    private var pullList = ArrayList<Pull>()
    private var adapter = PullAdapter(pullList, this)
    private lateinit var binding: ActivityPullsBinding
    private val client by lazy { InitializerClient.init() }

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
                fetchPulls(login, name)
            }
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
                        binding.recyclerPull.adapter =
                            PullAdapter(it, this@PullsActivity)
                        pullList.addAll(it)
                        loading()
                    }
                }
            }
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
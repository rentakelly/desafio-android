package br.com.rentakelly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rentakelly.databinding.ItemRepositoryBinding
import br.com.rentakelly.models.Repo

class RepositoryAdapter(
    private val repos: List<Repo>
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    class ViewHolder(
        private val itemRepositoryBinding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(itemRepositoryBinding.root) {
        fun binding(repo: Repo) {
            itemRepositoryBinding.tvName.text = repo.fullName
            itemRepositoryBinding.tvDescription.text = repo.description
            itemRepositoryBinding.tvFork.text = repo.forks.toString()
            itemRepositoryBinding.tvStars.text = repo.stars.toString()



        }

    }
}

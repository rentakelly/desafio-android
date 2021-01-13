package br.com.rentakelly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rentakelly.activities.limitePages
import br.com.rentakelly.databinding.ItemRepositoryBinding
import br.com.rentakelly.models.Repo
import com.bumptech.glide.Glide

class RepositoryAdapter(
    private val repoListener: RepoListener
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private val repos = mutableListOf<Repo>()

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
        if (position >= limitePages) repoListener.onThresholdReached()
        holder.itemRepositoryBinding.repoItem.setOnClickListener {
            repoListener.onRepoClickListener(repos[position])
        }
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    fun addRepos(newRepo: List<Repo>) {
        val index = repos.size
        repos.addAll(newRepo)
        notifyItemRangeChanged(index, newRepo.size)
    }

    class ViewHolder(
        val itemRepositoryBinding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(itemRepositoryBinding.root) {
        fun binding(repo: Repo) {
            itemRepositoryBinding.tvName.text = repo.fullName
            itemRepositoryBinding.tvDescription.text = repo.description
            itemRepositoryBinding.tvFork.text = repo.forks.toString()
            itemRepositoryBinding.tvStars.text = repo.stars.toString()
            itemRepositoryBinding.tvUsername.text = repo.owner.login
            Glide.with(itemView.context)
                .load(repo.owner.avatar)
                .circleCrop()
                .into(itemRepositoryBinding.imgUser)
        }
    }

    interface RepoListener {
        fun onRepoClickListener(repo: Repo)
        fun onThresholdReached()
    }
}

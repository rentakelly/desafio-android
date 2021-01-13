package br.com.rentakelly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rentakelly.databinding.ItemPullBinding
import br.com.rentakelly.models.Pull
import com.bumptech.glide.Glide

class PullAdapter(
    val pull: List<Pull>,
    private val pullClick: onPullClickListener


) : RecyclerView.Adapter<PullAdapter.PullViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullViewHolder {
        return PullViewHolder(
            ItemPullBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false

            )
        )
    }

    override fun onBindViewHolder(holder: PullViewHolder, position: Int) {
        holder.binding(this.pull[position])
        holder.itemPullBinding.pullItem.setOnClickListener {
            pullClick.onPullClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return pull.size
    }

    inner class PullViewHolder(
        val itemPullBinding: ItemPullBinding
    ) : RecyclerView.ViewHolder(itemPullBinding.root) {
        fun binding(pull: Pull) {
            itemPullBinding.tvUsername.text = pull.user.login
            itemPullBinding.tvDescriptionPull.text = pull.description
            itemPullBinding.tvPullname.text = pull.title
            Glide.with(itemView.context)
                .load(pull.user.avatar)
                .circleCrop()
                .into(itemPullBinding.imgUser)
        }
    }

    interface onPullClickListener {
        fun onPullClickListener(position: Int)
    }
}






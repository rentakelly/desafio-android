package br.com.rentakelly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rentakelly.databinding.ItemPullBinding
import br.com.rentakelly.models.Pull

class PullAdapter(
    private val pull: List<Pull>,
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

    override fun onBindViewHolder(holder: PullAdapter.PullViewHolder, position: Int) {
        holder.binding(pull[position])
        holder.itemPullBinding.pullItem.setOnClickListener {
            pullClick.onPullClickListener(pull[position])
        }
    }

    override fun getItemCount(): Int {
        return pull.size
    }

    inner class PullViewHolder(
        val itemPullBinding: ItemPullBinding
    ) : RecyclerView.ViewHolder(itemPullBinding.root) {
        fun binding(pull: Pull) {
            itemPullBinding.tvUsername.text = pull.user.toString()
            itemPullBinding.tvDescriptionPull.text = pull.description
            itemPullBinding.tvPullname.text = pull.title
//            Glide.with(itemView.context)
//                .load(repo.owner.avatar)
//                .circleCrop()
//                .into(itemPullBinding.imgUser)


        }

    }

    interface onPullClickListener {
        fun onPullClickListener(pull: Pull)

    }
}






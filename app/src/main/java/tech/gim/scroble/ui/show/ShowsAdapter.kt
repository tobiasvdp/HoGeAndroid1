package tech.gim.scroble.ui.show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.gim.scroble.databinding.MinimizedShowCardBinding
import tech.gim.scroble.model.MinimizedShow
import tech.gim.scroble.model.MinimizedShowWithImages
import timber.log.Timber

class ShowsRecyclerViewAdapter(var onClickHandler: (View, MinimizedShow?) -> Unit) : ListAdapter<MinimizedShowWithImages?, ShowsViewHolder>(ShowsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsViewHolder {
        return ShowsViewHolder.from(parent, onClickHandler)
    }

    override fun onBindViewHolder(holder: ShowsViewHolder, position: Int) {
        val minimizedShowWithImages = getItem(position)
        holder.binding.show = minimizedShowWithImages?.show
        holder.binding.images = minimizedShowWithImages?.images
    }

    fun dataChanged(newData: List<MinimizedShowWithImages?>?) {
        Timber.i("Changed dataset")
        if (newData == null) {
            submitList(ArrayList())
        } else {
            submitList(newData)
        }
    }
}

class ShowsViewHolder(val binding: MinimizedShowCardBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup, onClickHandler: (View, MinimizedShow?) -> Unit): ShowsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MinimizedShowCardBinding.inflate(layoutInflater, parent, false)
            binding.showCard.setOnClickListener(
                View.OnClickListener {
                    onClickHandler.invoke(it, binding.show)
                }
            )
            return ShowsViewHolder(binding)
        }
    }
}

class ShowsDiffUtil() : DiffUtil.ItemCallback<MinimizedShowWithImages?>() {
    override fun areItemsTheSame(oldItem: MinimizedShowWithImages, newItem: MinimizedShowWithImages): Boolean {
        return newItem.show?.ids != null && oldItem.show?.ids?.trakt == newItem.show?.ids?.trakt
    }

    override fun areContentsTheSame(oldItem: MinimizedShowWithImages, newItem: MinimizedShowWithImages): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}

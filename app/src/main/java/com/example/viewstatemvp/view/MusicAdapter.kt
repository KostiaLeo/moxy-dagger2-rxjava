package com.example.viewstatemvp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.viewstatemvp.databinding.ListItemBinding
import com.example.viewstatemvp.model.Results

class MusicAdapter(private val itemClickListener: (Results) -> Unit) : ListAdapter<Results, MusicViewHolder>(DiffUtilCallback()) {

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder =
        MusicViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClickListener(getItem(position))
        }
    }
}

class MusicViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(results: Results) {
        binding.artist = results
        binding.executePendingBindings()
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Results>() {
    override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean =
        oldItem.name == newItem.name && oldItem.image.url == newItem.image.url
}
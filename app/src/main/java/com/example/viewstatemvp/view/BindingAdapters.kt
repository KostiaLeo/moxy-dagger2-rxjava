package com.example.viewstatemvp.view

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewstatemvp.model.Results

@BindingAdapter("url")
fun bindImage(imageView: ImageView?, url: String) {
        val imgUri = Uri.parse(url).buildUpon().scheme("https").build()
        imageView?.let {
            Glide.with(it.context)
                .load(imgUri)
                .into(it)
//            .apply {
//                RequestOptions()
//                    .placeholder(R.drawable.loading_animation)
//                    .error(R.drawable.ic_broken_image)
//            }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Results>?) {
    val adapter = recyclerView.adapter as MusicAdapter
    adapter.submitList(data)
}
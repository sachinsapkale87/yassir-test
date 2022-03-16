package com.sachin.test.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sachin.test.databinding.RecItemBinding

class HomeAdapter(val clickListener:ListClickListener,val listMovie :List<PageObj.Movie>) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: RecItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater  = LayoutInflater.from(parent.context)
        val binder = RecItemBinding.inflate(layoutInflater,parent,false)
        return ItemViewHolder(binder)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val movieObj = listMovie.get(position)
        val url = "https://image.tmdb.org/t/p/w500/@path".replace("@path",movieObj.posterPath)
        Glide
            .with(holder.binding.itemImage.context)
            .load(url)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .into(holder.binding.itemImage)
        holder.binding.parentView.setOnClickListener{clickListener.onImageClick(movieObj.id,holder.binding.itemImage)}
        holder.binding.itemTitle.text = movieObj.title

    }

    override fun getItemCount(): Int {
        return listMovie.size
    }
    interface ListClickListener{
        fun onImageClick(id:Int,v: View)
    }
}
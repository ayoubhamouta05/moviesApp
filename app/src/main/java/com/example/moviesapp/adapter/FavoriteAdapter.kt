package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowFavoriteMoviesBinding
import com.example.moviesapp.model.favorite.FavoriteData

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowFavoriteMoviesBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var differCallback = object : DiffUtil.ItemCallback<FavoriteData>() {
        override fun areItemsTheSame(oldItem: FavoriteData, newItem: FavoriteData): Boolean {
            return oldItem.movieName == newItem.movieName && oldItem.movieImg == newItem.movieImg
        }

        override fun areContentsTheSame(oldItem: FavoriteData, newItem: FavoriteData): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        return ViewHolder(
            RowFavoriteMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            Glide.with(root).load(list.movieImg).into(movieImg)
            movieDescription.text = list.movieDescription
            movieTitle.text = list.movieName
            movieRate.text = list.movieRate
            var genre = ""
            for (i in 0 until list.movieGenre.size) {
                if (i > 0) {
                    genre += "/"
                }
                genre += list.movieGenre[i]
            }
            movieGenre.text = genre
            movieRate.text = list.movieRate

            root.setOnClickListener {
                onItemClickListener?.let {
                    it(list)
                }
            }

            root.setOnLongClickListener {
                onLongItemCLickListener?.let {
                    it(list)
                }
                true
            }
        }


    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((FavoriteData) -> Unit)? = null

    fun setOnItemClickListener(listener: ((FavoriteData) -> Unit)) {
        onItemClickListener = listener
    }

    private var onLongItemCLickListener: ((FavoriteData) -> Unit)? = null
    fun setOnLongItemClickListener(listener: ((FavoriteData) -> Unit)) {
        onLongItemCLickListener = listener
    }

}
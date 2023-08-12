package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowTopMoviesBinding
import com.example.moviesapp.model.topMovies.TopMoviesData


class TopMovieAdapter : RecyclerView.Adapter<TopMovieAdapter.ViewHolder>() {
    class ViewHolder(var binding: RowTopMoviesBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<TopMoviesData>() {
        override fun areItemsTheSame(oldItem: TopMoviesData, newItem: TopMoviesData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TopMoviesData, newItem: TopMoviesData): Boolean {
            return oldItem == newItem
        }

    }
    var differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowTopMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {

            Glide.with(root).load(list.image).into(movieImg)
            movieRate.text = list.rating
            movieName.text = list.title
            if (list.genre.isNotEmpty()) {
                var genre = ""
                for (i in 0 until list.genre.size) {
                    if (i > 0) {
                        genre += "/"
                    }
                    genre += list.genre[i]
                }
                movieGenre.text = genre
            }
            root.setOnClickListener {
                onItemClickListener?.let {
                    it(list)
                }
            }
        }
    }

    private var onItemClickListener: ((TopMoviesData) -> Unit)? = null
    fun setOnItemClickListener(listener: ((TopMoviesData) -> Unit)) {
        onItemClickListener = listener
    }

}
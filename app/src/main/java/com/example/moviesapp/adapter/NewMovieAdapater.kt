package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowNewMoviesBinding
import com.example.moviesapp.model.NewMoviesData

class NewMovieAdapter : RecyclerView.Adapter<NewMovieAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowNewMoviesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMovieAdapter.ViewHolder {
        return ViewHolder(RowNewMoviesBinding.inflate(
            LayoutInflater.from(
                parent.context),
                parent,
                false
        ))
    }

    private var differCallback = object : DiffUtil.ItemCallback<NewMoviesData>(){
        override fun areItemsTheSame(oldItem: NewMoviesData, newItem: NewMoviesData): Boolean {
            return  oldItem.movieName == newItem.movieName
        }

        override fun areContentsTheSame(oldItem: NewMoviesData, newItem: NewMoviesData): Boolean {
            return oldItem.movieImg == newItem.movieImg && oldItem.movieRate == newItem.movieRate
        }
    }
    var differ = AsyncListDiffer(this,differCallback)

    override fun onBindViewHolder(holder: NewMovieAdapter.ViewHolder, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            Glide.with(this.root).load(list.movieImg).into(movieImg)
            movieName.text = list.movieName
            var genre = ""
            for(i in 0 until list.movieGenre.size){
                if (i>0){
                   genre+="/"
                }
                genre += list.movieGenre[i]
            }
            movieGenre.text = genre
            movieRate.text = list.movieRate

            root.setOnClickListener{
                onItemClickListener?.let { it(list) }
            }

        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener : ((NewMoviesData) -> Unit)? = null
    fun setOnItemClickListener(listener : ((NewMoviesData) -> Unit)){
        onItemClickListener = listener
    }
}
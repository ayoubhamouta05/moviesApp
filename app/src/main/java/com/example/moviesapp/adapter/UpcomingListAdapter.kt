package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowNewMoviesBinding
import com.example.moviesapp.model.upcomingmovies.UpcomingMoviesData

class UpcomingListAdapter : RecyclerView.Adapter<UpcomingListAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowNewMoviesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingListAdapter.ViewHolder {
        return ViewHolder(
            RowNewMoviesBinding.inflate(
            LayoutInflater.from(
                parent.context),
            parent,
            false
        ))
    }

    private var differCallback = object : DiffUtil.ItemCallback<UpcomingMoviesData>(){
        override fun areItemsTheSame(oldItem: UpcomingMoviesData, newItem: UpcomingMoviesData): Boolean {
            return  oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: UpcomingMoviesData, newItem: UpcomingMoviesData): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this,differCallback)

    override fun onBindViewHolder(holder: UpcomingListAdapter.ViewHolder, position: Int) {
        val list = differ.currentList[0].message[0].entries[position]
        holder.binding.apply {
            rateStar.visibility = View.GONE
            movieRate.visibility = View.GONE

            Glide.with(root).load(list.imageModel.url).into(movieImg)
            movieName.text = list.titleText
            var genre = ""
            for(i in 0 until list.genres.size){
                if (i>0){
                    genre+="/"
                }
                genre += list.genres[i]
            }
            movieGenre.text = genre
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener : ((UpcomingMoviesData) -> Unit)? = null
    fun setOnItemClickListener(listener : ((UpcomingMoviesData) -> Unit)){
        onItemClickListener = listener
    }
}
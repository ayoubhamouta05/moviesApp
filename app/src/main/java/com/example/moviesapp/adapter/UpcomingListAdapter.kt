package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowNewMoviesBinding
import com.example.moviesapp.model.upcomingmovies.Entry
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

    private var differCallback = object : DiffUtil.ItemCallback<Entry>(){
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return  oldItem.titleText == newItem.titleText
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this,differCallback)

    override fun onBindViewHolder(holder: UpcomingListAdapter.ViewHolder, position: Int) {
        val list = differ.currentList[position]
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
            root.setOnClickListener {
                onItemClickListener?.let {
                    it(list)
                }
            }

        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener : ((Entry) -> Unit)? = null
    fun setOnItemClickListener(listener : ((Entry) -> Unit)){
        onItemClickListener = listener
    }
}
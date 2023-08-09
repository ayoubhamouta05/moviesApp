package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowProfileMoviesBinding
import com.example.moviesapp.databinding.RowUpcomingMoviesBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowProfileMoviesBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        return ViewHolder(
            RowProfileMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(this.root).load(differ.currentList[position]).into(movieImg)

            movieImg.setOnClickListener {
                onItemClickListener?.let {
                    it(differ.currentList[position])
                }
            }

        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener : ((String) -> Unit)? = null
    fun setOnItemClickListener(listener : ((String) -> Unit)){
        onItemClickListener = listener
    }

}
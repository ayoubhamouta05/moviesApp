package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowCategoryBinding
import com.example.moviesapp.model.category.CategoryData

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var differCallback = object : DiffUtil.ItemCallback<CategoryData>() {
        override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem.CategoryName == newItem.CategoryName
        }

        override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        return ViewHolder(
            RowCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        var list = differ.currentList[position]
        holder.binding.apply {
            Glide.with(root).load(list.categoryImg).into(categoryImg)
            categoryName.text = list.CategoryName
            root.setOnClickListener {
                onItemClickListener?.let { it(list.CategoryName) }
            }

        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener : ((String) -> Unit)? = null
    fun setOnItemClickListener(listener : ((String) -> Unit)){
        onItemClickListener = listener
    }
}

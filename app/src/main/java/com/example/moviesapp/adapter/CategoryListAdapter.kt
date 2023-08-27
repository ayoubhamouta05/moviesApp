package com.example.moviesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.databinding.RowMoviesOfCategoryBinding
import com.example.moviesapp.model.movieOfCategory.Result
import com.example.moviesapp.model.search.SearchData
import com.example.moviesapp.model.topMovies.TopMoviesData
import com.example.moviesapp.model.upcomingmovies.Entry

class CategoryListAdapter(var dataType: String?) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: RowMoviesOfCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryListAdapter.ViewHolder {
        return ViewHolder(
            RowMoviesOfCategoryBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    private var differCallbackUpcoming = object : DiffUtil.ItemCallback<Entry>() {
        override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem.titleText == newItem.titleText
        }

        override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
            return oldItem == newItem
        }
    }
    var differUpcoming = AsyncListDiffer(this, differCallbackUpcoming)


    private var differCallbackTop = object : DiffUtil.ItemCallback<TopMoviesData>() {
        override fun areItemsTheSame(oldItem: TopMoviesData, newItem: TopMoviesData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TopMoviesData, newItem: TopMoviesData): Boolean {
            return oldItem == newItem
        }
    }
    var differTop = AsyncListDiffer(this, differCallbackTop)


    private var differCallbackCategory = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    var differCategory = AsyncListDiffer(this, differCallbackCategory)


    private var differCallbackRecently = object : DiffUtil.ItemCallback<TopMoviesData>() {
        override fun areItemsTheSame(oldItem: TopMoviesData, newItem: TopMoviesData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: TopMoviesData, newItem: TopMoviesData): Boolean {
            return oldItem == newItem
        }
    }
    var differRecently = AsyncListDiffer(this, differCallbackRecently)

    private var differCallbackSearch = object : DiffUtil.ItemCallback<com.example.moviesapp.model.search.Result>() {
        override fun areItemsTheSame(oldItem: com.example.moviesapp.model.search.Result, newItem: com.example.moviesapp.model.search.Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: com.example.moviesapp.model.search.Result, newItem: com.example.moviesapp.model.search.Result): Boolean {
            return oldItem == newItem
        }
    }
    var differSearch = AsyncListDiffer(this, differCallbackSearch)


    override fun onBindViewHolder(holder: CategoryListAdapter.ViewHolder, position: Int) {

        if (dataType == "Upcoming") {
            val list = differUpcoming.currentList[position]
            holder.binding.apply {
                rateStar.visibility = View.GONE
                movieRate.visibility = View.GONE

                Glide.with(root).load(list.imageModel.url).into(movieImg)
                movieName.text = list.titleText
                var genre = ""
                for (i in 0 until list.genres.size) {
                    if (i > 0) {
                        genre += "/"
                    }
                    genre += list.genres[i]
                }
                movieGenre.text = genre
                root.setOnClickListener {
                    onUpcomingItemClickListener?.let {
                        it(list)
                    }
                }
            }
        } else if (dataType == "Top") {
            val list = differTop.currentList[position]
            holder.binding.apply {
                Glide.with(root).load(list.image).into(movieImg)
                movieName.text = list.title
                var genre = ""
                for (i in 0 until list.genre.size) {
                    if (i > 0) {
                        genre += "/"
                    }
                    genre += list.genre[i]
                }
                movieGenre.text = genre
                root.setOnClickListener {
                    onTopItemClickListener?.let {
                        it(list)
                    }
                }
            }
        } else if (dataType == "watched") {
            val list = differRecently.currentList[position]

            holder.binding.apply {
                Glide.with(root).load(list.image).into(movieImg)
                movieName.text = list.title
                var genre = ""
                for (i in 0 until list.genre.size) {
                    if (i > 0) {
                        genre += "/"
                    }
                    genre += list.genre[i]
                }
                movieGenre.text = genre
                root.setOnClickListener {
                    onRecentlyItemClickListener?.let {
                        it(list)
                    }
                }
            }
        } else if(dataType == "Search"){
            val list = differSearch.currentList[position]
            holder.binding.apply {
                Glide.with(root).load(list.poster_path).into(movieImg)
                movieName.text = list.title
                movieRate.text = list.vote_average.toString()
                movieGenre.text = "action/drama"
                root.setOnClickListener {
                    onSearchItemClickListener?.let {
                        it(list)
                    }
                }
            }
        } else {
            val list = differCategory.currentList[position]
            holder.binding.apply {
                Glide.with(root).load(list.poster_path).into(movieImg)
                movieName.text = list.title
                movieRate.text = list.vote_average
                movieGenre.text = dataType
                root.setOnClickListener {
                    onCategoryItemClickListener?.let {
                        it(list)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return if (dataType == "Upcoming") {
            differUpcoming.currentList.size
        } else if (dataType == "Top") {
            differTop.currentList.size
        } else if (dataType == "watched") {
            differRecently.currentList.size
        } else if(dataType == "Search"){
            differSearch.currentList.size
        } else {
            differCategory.currentList.size
        }
    }

    private var onUpcomingItemClickListener: ((Entry) -> Unit)? = null
    fun setOnUpcomingItemClickListener(listener: ((Entry) -> Unit)) {
        onUpcomingItemClickListener = listener
    }

    private var onTopItemClickListener: ((TopMoviesData) -> Unit)? = null
    fun setOnTopItemClickListener(listener: ((TopMoviesData) -> Unit)) {
        onTopItemClickListener = listener
    }

    private var onCategoryItemClickListener: ((Result) -> Unit)? = null
    fun setOnCategoryItemClickListener(listener: ((Result) -> Unit)) {
        onCategoryItemClickListener = listener
    }

    private var onRecentlyItemClickListener: ((TopMoviesData) -> Unit)? = null
    fun setOnRecentlyItemClickListener(listener: ((TopMoviesData) -> Unit)) {
        onRecentlyItemClickListener = listener
    }

    private var onSearchItemClickListener: ((com.example.moviesapp.model.search.Result) -> Unit)? = null
    fun setOnSearchItemClickListener(listener: ((com.example.moviesapp.model.search.Result) -> Unit)) {
        onSearchItemClickListener = listener
    }
}
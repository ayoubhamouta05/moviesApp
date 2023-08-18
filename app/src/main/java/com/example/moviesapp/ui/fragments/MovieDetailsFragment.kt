package com.example.moviesapp.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.moviesapp.R
import com.example.moviesapp.adapter.UpcomingAdapter
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.model.favorite.FavoriteData
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    lateinit var recommendedAdapter: UpcomingAdapter
    private val movieDetailsFragmentArgs: MovieDetailsFragmentArgs by navArgs()
    private var typeOfData = ""
    lateinit var viewModel: MoviesViewModel
    var isFavorite = false
    var isWatched = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupUpcomingRv()
        retrieveMovieData()


        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        recommendedAdapter.setOnItemClickListener { movieImgUrl ->
            changeMoviesFromRecommended(movieImgUrl)
        }

        binding.seeAllRecommended.setOnClickListener {
            val data = Bundle().apply {
                putString("dataType","Top")
            }
            findNavController().navigate(R.id.action_movieDetailsFragment_to_moviesCategoryFragment,data)
        }

        binding.heartCv.setOnClickListener {
            setMovieAsFavorite()
        }
        isFavorite = if (checkIfIsFavorite()){
            binding.heartImg.setImageResource(R.drawable.ic_red_heart)
            true
        }else{
            binding.heartImg.setImageResource(R.drawable.ic_heart)
            false
        }

        binding.addToWatchedCv.setOnClickListener {
            setMovieAsWatched()
        }
        isWatched = if (checkIfIsWatched()){
            binding.addToWatchedImg.setImageResource(R.drawable.ic_done)
            true
        }else{
            binding.addToWatchedImg.setImageResource(R.drawable.ic_add)
            false
        }

        binding.shareBtn.setOnClickListener {
            shareMovie()
        }

        binding.downloadBtn.setOnClickListener {
            Toast.makeText(requireContext(), "This feature is not currently available", Toast.LENGTH_SHORT).show()
        }


    }

    private fun shareMovie(){
        var textInSmall = ""
        for (i in binding.movieName.text.toString()){
            textInSmall+=i.lowercaseChar()
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Watch This Amazing Movie")
            putExtra(Intent.EXTRA_TEXT, "https://watch.plex.tv/movie/${textInSmall.replace(" ","-")}" )
        }

        startActivity(intent)
    }

    private fun checkIfIsWatched(): Boolean {
        val currentList = viewModel.recentlyWatched.value ?: mutableListOf()
        val currentListMovie = viewModel.top100Movies.value ?: mutableListOf()
        for (i in currentListMovie){
                if(binding.movieName.text == i.title && binding.moviesDescription.text == i.description ){
                    if(currentList.contains(i.image))
                        return true
            }
        }
        return false
    }

    private fun checkIfIsFavorite() : Boolean{
        val currentList = viewModel.favoriteMovies.value ?: mutableListOf()
        for (i in currentList) {
            if (i.movieName == binding.movieName.text.toString() && i.movieDescription == binding.moviesDescription.text.toString() && i.movieGenre == binding.movieGenre.text.split(
                    "/"
                )
            ) {
                return true
            }
        }
        return false
    }

    private fun changeMoviesFromRecommended(movieImgUrl: String) {
        viewModel.top100Movies.observe(requireActivity()) {
            Log.d("MovieDetailsFragment", it.size.toString())
            for (element in it) {
                if (element.image == movieImgUrl) {
                    val data = Bundle().apply {
                        putSerializable("movieTopData", element)
                    }
                    Log.d("MovieDetailsFragment", element.toString())
                    findNavController().navigate(R.id.action_movieDetailsFragment_self, data)
                    break
                }
            }
        }
    }

    private fun setupUpcomingRv() {
        recommendedAdapter = UpcomingAdapter()

        binding.rvRecommendedMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendedAdapter
            try {
                viewModel.top100Movies.observe(requireActivity()) {
                    val list = arrayListOf<String>()
                    for (i in 40 until 55) {
                        list.add(it[i].image)
                    }
                    recommendedAdapter.differ.submitList(list)
                }

            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun retrieveMovieData() {

        if (movieDetailsFragmentArgs.movieTopData != null) {
            movieDetailsFragmentArgs.movieTopData?.let {
                try {
                    Glide.with(requireContext()).load(it.image).into(binding.movieBigImg)
                    Glide.with(requireContext()).load(it.image).into(binding.movieSmallImg)
                    binding.movieName.text = it.title
                    binding.moviesDescription.text = it.description
                    if (it.genre.isNotEmpty()) {
                        var genre = ""
                        for (i in 0 until it.genre.size) {
                            if (i > 0) {
                                genre += "/"
                            }
                            genre += it.genre[i]
                        }
                        binding.movieGenre.text = genre
                    }
                    typeOfData = "Top"
                } catch (ex: Exception) {
                    Log.d("MovieDetailsFragment", ex.message.toString())
                    Toast.makeText(
                        requireContext(),
                        "sorry we can't retrieve this data\nplease try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else if (movieDetailsFragmentArgs.movieUpcData != null) {
            movieDetailsFragmentArgs.movieUpcData?.let {
                try {
                    Glide.with(requireContext()).load(it.imageModel.url).into(binding.movieBigImg)
                    Glide.with(requireContext()).load(it.imageModel.url).into(binding.movieSmallImg)
                    binding.movieName.text = it.titleText
                    binding.moviesDescription.text = it.imageModel.caption
                    if (it.genres.isNotEmpty()) {
                        var genre = ""
                        for (i in 0 until it.genres.size) {
                            if (i > 0) {
                                genre += "/"
                            }
                            genre += it.genres[i]
                        }
                        binding.movieGenre.text = genre
                    }
                    typeOfData = "Upcoming"
                } catch (ex: Exception) {
                    Log.d("MovieDetailsFragment", ex.message.toString())
                    Toast.makeText(
                        requireContext(),
                        "sorry we can't retrieve this data\nplease try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else if (movieDetailsFragmentArgs.movieFavoriteData != null) {
            movieDetailsFragmentArgs.movieFavoriteData?.let {
                try {
                    binding.heartImg.setImageResource(R.drawable.ic_red_heart)
                    isFavorite = true
                    Glide.with(requireContext()).load(it.movieImg).into(binding.movieBigImg)
                    Glide.with(requireContext()).load(it.movieImg).into(binding.movieSmallImg)
                    binding.movieName.text = it.movieName
                    binding.moviesDescription.text = it.movieDescription
                    if (it.movieGenre.isNotEmpty()) {
                        var genre = ""
                        for (i in 0 until it.movieGenre.size) {
                            if (i > 0) {
                                genre += "/"
                            }
                            genre += it.movieGenre[i]
                        }
                        binding.movieGenre.text = genre
                    }
                    typeOfData = "Favorite"
                } catch (ex: Exception) {
                    Log.d("MovieDetailsFragment", ex.message.toString())
                    Toast.makeText(
                        requireContext(),
                        "sorry we can't retrieve this data\nplease try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            movieDetailsFragmentArgs.movieOfCategory?.let {
                try {
                    Glide.with(requireContext()).load(it.poster_path).into(binding.movieBigImg)
                    Glide.with(requireContext()).load(it.poster_path).into(binding.movieSmallImg)
                    binding.moviesDescription.text = it.overview
                    typeOfData = "Category"
                } catch (ex: Exception) {
                    Log.d("MovieDetailsFragment", ex.message.toString())
                    Toast.makeText(
                        requireContext(),
                        "sorry we can't retrieve this data\nplease try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setMovieAsFavorite() {
        isFavorite = !isFavorite
        val currentList = viewModel.favoriteMovies.value ?: mutableListOf()
        if (isFavorite) {
            binding.heartImg.setImageResource(R.drawable.ic_red_heart)
            if (typeOfData == "Top") {
                movieDetailsFragmentArgs.movieTopData!!.apply {
                    try {
                        currentList.add(FavoriteData(
                            this.image, this.title, this.description, this.genre, this.rating
                        ))
                        viewModel.favoriteMovies.postValue(currentList)

                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }

                }
            } else if (typeOfData == "Upcoming") {
                movieDetailsFragmentArgs.movieUpcData!!.apply {
                    try {
                        currentList.add(
                            FavoriteData(
                                this.imageModel.url,
                                this.titleText,
                                this.imageModel.caption,
                                this.genres as ArrayList,
                                "not Published yet"
                            )
                        )

                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }

                }
            } else {
                movieDetailsFragmentArgs.movieOfCategory!!.apply {
                    try {
                        currentList.add(
                            FavoriteData(
                                this.poster_path,
                                this.title,
                                this.overview,
                                arrayListOf(binding.movieGenre.text.toString()),
                                this.vote_average
                            )
                        )


                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }

                }
            }

        } else {
            binding.heartImg.setImageResource(R.drawable.ic_heart)
            for (i in currentList) {
                if (i.movieName == binding.movieName.text.toString() && i.movieDescription == binding.moviesDescription.text.toString() && i.movieGenre == binding.movieGenre.text.split(
                        "/"
                    )
                ) {
                    currentList.remove(i)
                    break
                }
            }
        }
        viewModel.favoriteMovies.postValue(currentList)
    }

    private fun setMovieAsWatched() {
        isWatched = !isWatched
        val currentList = viewModel.recentlyWatched.value ?: mutableListOf()
        if(isWatched){
            binding.addToWatchedImg.setImageResource(R.drawable.ic_done)
            if (typeOfData == "Top") {
                movieDetailsFragmentArgs.movieTopData!!.apply {
                    try {
                        currentList.add(this.image)
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            } else if (typeOfData == "Upcoming") {
                movieDetailsFragmentArgs.movieUpcData!!.apply {
                    try {
                        currentList.add(this.imageModel.url)
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            } else {
                movieDetailsFragmentArgs.movieOfCategory!!.apply {
                    try {
                        currentList.add(this.poster_path)
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            }
        } else {
            binding.addToWatchedImg.setImageResource(R.drawable.ic_add)
            val currentListMovie = viewModel.top100Movies.value ?: mutableListOf()
            for (i in currentListMovie){
                if(binding.movieName.text == i.title && binding.moviesDescription.text == i.description ){
                    currentList.remove(i.image)
                }
            }
        }
        viewModel.recentlyWatched.postValue(currentList)

    }

}

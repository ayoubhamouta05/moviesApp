package com.example.moviesapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
                putString("dataType", "Top")
            }
            findNavController().navigate(
                R.id.action_movieDetailsFragment_to_moviesCategoryFragment,
                data
            )
        }

        binding.heartCv.setOnClickListener {
            setMovieAsFavorite()
        }
        isFavorite = if (checkIfIsFavorite()) {
            binding.heartImg.setImageResource(R.drawable.ic_red_heart)
            true
        } else {
            binding.heartImg.setImageResource(R.drawable.ic_heart)
            false
        }

        binding.addToWatchedCv.setOnClickListener {
            setMovieAsWatched()
        }
        isWatched = if (checkIfIsWatched()) {
            binding.addToWatchedImg.setImageResource(R.drawable.ic_done)
            true
        } else {
            binding.addToWatchedImg.setImageResource(R.drawable.ic_add)
            false
        }

        binding.shareBtn.setOnClickListener {
            shareMovie()
        }
        binding.watchImg.setOnClickListener {
            watchMovie()
        }

        binding.downloadBtn.setOnClickListener {
            Toast.makeText(requireContext(), "This Feature Is Not Available Right Now", Toast.LENGTH_SHORT).show()
        }

    }


    private fun watchMovie(){
        var textInSmall = ""
        for (i in binding.movieName.text.toString()) {
            textInSmall += i.lowercaseChar()
        }
        val webUrl = "https://watch.plex.tv/movie/${textInSmall.replace(" ", "-")}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
        startActivity(intent)

    }

    private fun shareMovie() {
        var textInSmall = ""
        for (i in binding.movieName.text.toString()) {
            textInSmall += i.lowercaseChar()
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Watch This Amazing Movie")
            putExtra(
                Intent.EXTRA_TEXT,
                "https://watch.plex.tv/movie/${textInSmall.replace(" ", "-")}"
            )
        }

        startActivity(intent)
    }

    private fun checkIfIsWatched(): Boolean {
        val currentList = viewModel.recentlyWatched.value ?: mutableListOf()
        val currentListMovie = viewModel.top100Movies.value ?: mutableListOf()
        for (i in currentListMovie) {
            if (binding.movieName.text == i.title && binding.moviesDescription.text == i.description) {
                if (currentList.contains(i.image))
                    return true
            }
        }
        return false
    }

    private fun checkIfIsFavorite(): Boolean {
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

            for (element in it) {
                if (element.image == movieImgUrl) {
                    val data = Bundle().apply {
                        putSerializable("movieTopData", element)
                    }
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
                viewModel.top100Movies.observe(requireActivity()) { movies->
                    val listMovies = movies.shuffled()
                    val list = arrayListOf<String>()
                    for (i in 0 until 55) {
                        list.add(listMovies[i].image)
                    }
                    recommendedAdapter.differ.submitList(list)
                }

            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun retrieveMovieData() {
        var movieSmallImg = ""
        var movieBigImg = ""
        var movieName = ""
        var movieDescription = ""
        var genres = arrayListOf<String>()


        if (movieDetailsFragmentArgs.movieTopData != null) {

            movieDetailsFragmentArgs.movieTopData!!.let {
                try {
                    movieBigImg = it.image
                    movieSmallImg = it.image
                    movieName = it.title
                    movieDescription = it.description
                    genres = it.genre

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
            movieDetailsFragmentArgs.movieUpcData!!.let {
                try {

                    movieBigImg = it.imageModel.url
                    movieSmallImg = it.imageModel.url
                    movieName = it.titleText
                    movieDescription = it.imageModel.caption!!
                    genres = it.genres as ArrayList<String>

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
            movieDetailsFragmentArgs.movieFavoriteData!!.let {
                try {
                    // if it come from favorite fragment it means that it was already liked
                    binding.heartImg.setImageResource(R.drawable.ic_red_heart)
                    isFavorite = true

                    movieBigImg = it.movieImg
                    movieSmallImg = it.movieImg
                    movieName = it.movieName
                    movieDescription = it.movieDescription!!
                    genres = it.movieGenre

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

        } else if (movieDetailsFragmentArgs.movieSearchData != null) {
            movieDetailsFragmentArgs.movieSearchData!!.let {
                try {
                    movieBigImg = it.backdrop_path
                    movieSmallImg = it.poster_path
                    movieName = it.title
                    movieDescription = it.overview
                    genres = arrayListOf("not defined")

                    typeOfData = "Search"
                } catch (ex: Exception) {
                    Log.d("MovieDetailsFragment", ex.message.toString())
                    Toast.makeText(
                        requireContext(),
                        "sorry we can't retrieve this data\nplease try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }else {
            movieDetailsFragmentArgs.movieOfCategory!!.let {
                try {
                    movieBigImg = it.poster_path
                    movieSmallImg = it.poster_path
                    movieDescription = it.overview

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
        Glide.with(requireContext()).load(movieBigImg).into(binding.movieBigImg)
        Glide.with(requireContext()).load(movieSmallImg).into(binding.movieSmallImg)
        binding.movieName.text = movieName
        binding.moviesDescription.text = movieDescription
        if (genres.isNotEmpty()) {
            var genre = ""
            for (i in 0 until genres.size) {
                if (i > 0) {
                    genre += "/"
                }
                genre += genres[i]
            }
            binding.movieGenre.text = genre
        }
    }

    private fun setMovieAsFavorite() {
        isFavorite = !isFavorite
        var favoriteData = FavoriteData()

        if (isFavorite) {
            binding.heartImg.setImageResource(R.drawable.ic_red_heart)
            if (typeOfData == "Top") {
                movieDetailsFragmentArgs.movieTopData?.apply {
                    try {

                        favoriteData = FavoriteData(
                            this.image, this.title, this.description, this.genre, this.rating
                        )

                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }

                }
            } else if (typeOfData == "Upcoming") {
                movieDetailsFragmentArgs.movieUpcData?.apply {
                    try {
                        favoriteData = FavoriteData(
                            this.imageModel.url,
                            this.titleText,
                            this.imageModel.caption,
                            this.genres as ArrayList,
                            "not Published yet"
                        )

                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }

                }
            } else if (typeOfData == "Search") {
                movieDetailsFragmentArgs.movieSearchData?.apply {
                    try {
                        favoriteData = FavoriteData(
                            this.poster_path,
                            this.title,
                            this.overview,
                            arrayListOf("not defined"),
                            this.vote_average.toString()
                        )

                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }

                }
            }else {
                movieDetailsFragmentArgs.movieOfCategory?.apply {
                    try {
                        favoriteData = FavoriteData(
                            this.poster_path,
                            this.title,
                            this.overview,
                            arrayListOf(binding.movieGenre.text.toString()),
                            this.vote_average
                        )
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            }

            viewModel.setMoviesAsFavorite(favoriteData)
        } else {
            binding.heartImg.setImageResource(R.drawable.ic_heart)

            viewModel.deleteMovieFromFavorite(
                binding.movieName.text.toString(),
                binding.moviesDescription.text.toString(), binding.movieGenre.text.split(
                    "/"
                )
            )

        }
    }

    private fun setMovieAsWatched() {
        isWatched = !isWatched

        var imageUrl = ""
        if (isWatched) {
            binding.addToWatchedImg.setImageResource(R.drawable.ic_done)
            if (typeOfData == "Top") {
                movieDetailsFragmentArgs.movieTopData?.apply {
                    try {
                        imageUrl = this.image
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            } else if (typeOfData == "Upcoming") {
                movieDetailsFragmentArgs.movieUpcData?.apply {
                    try {
                        imageUrl = this.imageModel.url
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            } else if (typeOfData == "Search") {
                movieDetailsFragmentArgs.movieSearchData?.apply {
                    try {
                        imageUrl = this.poster_path
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            }else {
                movieDetailsFragmentArgs.movieOfCategory?.apply {
                    try {
                        imageUrl = this.poster_path
                    } catch (ex: Exception) {
                        Log.d("MovieDetailsFragment", ex.message.toString())
                    }
                }
            }

            viewModel.setMoviesAsWatched(imageUrl)
        } else {
            binding.addToWatchedImg.setImageResource(R.drawable.ic_add)
            viewModel.deleteMoviesFromRecently(
                binding.movieName.text.toString(),
                binding.moviesDescription.text.toString()
            )
        }

    }

}

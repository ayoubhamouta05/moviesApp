package com.example.moviesapp.ui.fragments

import android.os.Bundle
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
import com.example.moviesapp.adapter.UpcomingAdapter
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class MovieDetailsFragment : Fragment() {

    private lateinit var binding : FragmentMovieDetailsBinding
    lateinit var recommendedAdapter : UpcomingAdapter
    private val movieDetailsFragmentArgs: MovieDetailsFragmentArgs by navArgs()
    lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

      //  setupUpcomingRv()

        retrieveMovieData()

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setupUpcomingRv(){
        recommendedAdapter = UpcomingAdapter()

        // todo : not tested yet
        binding.rvRecommendedMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL , false)
            adapter = recommendedAdapter
            try {
                viewModel.top100Movies.observe(requireActivity()){
                    val list = arrayListOf<String>()
                    for (i in 40 until 55 ){
                        list.add(it[i].image)
                    }
                    recommendedAdapter.differ.submitList(list)
                }

            }catch (ex : Exception){
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }


    private fun retrieveMovieData(){

        if (movieDetailsFragmentArgs.movieTopData != null){
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
                }catch (ex : Exception){
                    Log.d("MovieDetailsFragment",ex.message.toString())
                    Toast.makeText(requireContext(), "sorry we can't retrieve this data\nplease try again later", Toast.LENGTH_SHORT).show()
                }
            }
        }else if (movieDetailsFragmentArgs.movieUpcData != null){
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
                }catch (ex : Exception){
                    Log.d("MovieDetailsFragment",ex.message.toString())
                    Toast.makeText(requireContext(), "sorry we can't retrieve this data\nplease try again later", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Log.d("MovieDetailsFragment","there is no data")
        }

    }

}
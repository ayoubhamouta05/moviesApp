package com.example.moviesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.adapter.TopMovieAdapter
import com.example.moviesapp.adapter.UpcomingAdapter
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupUpcomingRv()
        setupTopMovieRv()

        upcomingAdapter.setOnItemClickListener {upcomingMovieImg->
            viewModel.upcomingMovies.observe(requireActivity()){

                for(i in it){
                    if(i.imageModel.url == upcomingMovieImg) {
                        val data = Bundle().apply {
                            putSerializable("movieUpcData", i)
                        }
                        findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment,data)
                        break
                    }
                }
            }
        }

        binding.seeAllUpcomingMovie.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_moviesCategoryFragment)

        }

        topMovieAdapter.setOnItemClickListener {
            val data = Bundle().apply {
                putSerializable(
                    "movieTopData",it
                )
            }

            findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment , data)
        }

        binding.seeAllTopMovies.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_moviesCategoryFragment)
        }

    }

    private fun setupUpcomingRv() {
        upcomingAdapter = UpcomingAdapter()

        //todo : fix glide issue

        binding.rvUpcomingMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
            try {
                val list = arrayListOf<String>()
                viewModel.upcomingMovies.observe(requireActivity()) {
                    for (element in it) {
                        list.add(element.imageModel.url)
                    }
                    upcomingAdapter.differ.submitList(list)
                }

            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun setupTopMovieRv() {

        topMovieAdapter = TopMovieAdapter()

        binding.rvNewMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topMovieAdapter
            try {
                viewModel.top100Movies.observe(requireActivity()) {
                    topMovieAdapter.differ.submitList(it)
                }

            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }


}
package com.example.moviesapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.adapter.CategoryListAdapter
import com.example.moviesapp.databinding.FragmentMoviesCategoryBinding
import com.example.moviesapp.model.topMovies.TopMoviesData
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class MoviesCategoryFragment : Fragment() {

    private lateinit var binding: FragmentMoviesCategoryBinding
    private lateinit var categoryListAdapter: CategoryListAdapter

    private val args: MoviesCategoryFragmentArgs by navArgs()

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        viewModel.loadingCategoryProgressBar.observe(requireActivity()) {
            if (it)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }

        viewModel.getMoviesWithCategory(args.dataType)

        setupRv(args.dataType)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        categoryListAdapter.setOnUpcomingItemClickListener {

            val data = Bundle().apply {
                putSerializable(
                    "movieUpcData", it
                )
            }
            findNavController().navigate(
                R.id.action_moviesCategoryFragment_to_movieDetailsFragment,
                data
            )
        }

        categoryListAdapter.setOnTopItemClickListener {

            val data = Bundle().apply {
                putSerializable(
                    "movieTopData", it
                )
            }
            findNavController().navigate(
                R.id.action_moviesCategoryFragment_to_movieDetailsFragment,
                data
            )

        }
        categoryListAdapter.setOnCategoryItemClickListener {
            val data = Bundle().apply {
                putSerializable(
                    "movieOfCategory", it
                )
            }
            findNavController().navigate(
                R.id.action_moviesCategoryFragment_to_movieDetailsFragment,
                data
            )
        }

        categoryListAdapter.setOnRecentlyItemClickListener {
            val data = Bundle().apply {
                putSerializable("movieTopData", it)
            }
            findNavController().navigate(
                R.id.action_moviesCategoryFragment_to_movieDetailsFragment,
                data
            )
        }

    }

    private fun setupRv(dataType: String) {

        categoryListAdapter = CategoryListAdapter(dataType)
        binding.rvCategoryMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = categoryListAdapter
            if (dataType == "Upcoming") {
                viewModel.upcomingMovies.observe(requireActivity()) {
                    categoryListAdapter.differUpcoming.submitList(it)
                }
            } else if (dataType == "Top") {
                viewModel.top100Movies.observe(requireActivity()) {
                    categoryListAdapter.differTop.submitList(it)
                }

            } else if (dataType == "watched") {

                val currentListTop = viewModel.top100Movies.value ?: mutableListOf()
                val currentListRecently = viewModel.recentlyWatched.value ?: mutableListOf()
                val dataList = mutableListOf<TopMoviesData>()
                for (recently in currentListRecently) {
                    for (topMovie in currentListTop) {
                        if (topMovie.image == recently) {
                            dataList.add(topMovie)
                        }
                    }
                }
                categoryListAdapter.differRecently.submitList(dataList)
            } else {
                viewModel.moviesOfCategory.observe(requireActivity()) {
                    categoryListAdapter.differCategory.submitList(it)
                }
            }

        }
    }


}
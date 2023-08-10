package com.example.moviesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.adapter.MoviesCategoryAdapter
import com.example.moviesapp.adapter.UpcomingListAdapter
import com.example.moviesapp.databinding.FragmentMoviesCategoryBinding
import com.example.moviesapp.model.NewMoviesData
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class MoviesCategoryFragment : Fragment() {

    private lateinit var binding : FragmentMoviesCategoryBinding
    private lateinit var moviesAdapter: MoviesCategoryAdapter
    private lateinit var upcomingListAdapter : UpcomingListAdapter

    lateinit var viewModel : MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentMoviesCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setupCategoryRv()
        moviesAdapter = MoviesCategoryAdapter()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        moviesAdapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_moviesCategoryFragment_to_movieDetailsFragment)
        }

        upcomingListAdapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_moviesCategoryFragment_to_movieDetailsFragment)
        }

        viewModel = (activity as MainActivity).viewModel
        setupUpcomingRv()

    }

    private fun setupUpcomingRv(){

        upcomingListAdapter = UpcomingListAdapter()
        binding.rvCategoryMovies.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = upcomingListAdapter
            viewModel.upcomingMovies.observe(requireActivity()){
                upcomingListAdapter.differ.submitList(it)
            }

        }
    }


    private fun setupCategoryRv(){
        var list = arrayListOf(
            NewMoviesData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "NewsPaper",
                arrayListOf("drama", "action"),
                "7.8"
            ),
            NewMoviesData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "NewsPaper",
                arrayListOf("drama", "action", "Comedy"),
                "7.8"
            ),
            NewMoviesData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "NewsPaper",
                arrayListOf("drama", "action"),
                "7.8"
            ),
            NewMoviesData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "NewsPaper",
                arrayListOf("drama", "action"),
                "7.8"
            ),
            NewMoviesData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "NewsPaper",
                arrayListOf("drama", "action"),
                "7.8"
            ),
            NewMoviesData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "NewsPaper",
                arrayListOf("drama", "action"),
                "7.8"
            ),

            )
        moviesAdapter = MoviesCategoryAdapter()
        binding.rvCategoryMovies.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = moviesAdapter
            moviesAdapter.differ.submitList(list)
        }
    }

}
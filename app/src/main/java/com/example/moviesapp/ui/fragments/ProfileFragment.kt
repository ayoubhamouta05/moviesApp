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
import com.example.moviesapp.adapter.ProfileAdapter
import com.example.moviesapp.adapter.UpcomingAdapter
import com.example.moviesapp.databinding.FragmentProfileBinding
import com.example.moviesapp.model.favorite.FavoriteData
import com.example.moviesapp.model.topMovies.TopMoviesData
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var recentlyAdapter : ProfileAdapter
    private lateinit var favoritesAdapter : ProfileAdapter
    private lateinit var viewModel : MoviesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        // todo : add button to swipe up the user information and do some animation
        setupRecentlyRv()
        setupFavoritesRv()

        recentlyAdapter.setOnItemClickListener {
            val currentList = viewModel.top100Movies.value ?: mutableListOf()
            for (i in currentList){
                if (i.image == it){
                    val data = Bundle().apply {
                        putSerializable("movieTopData",i)
                    }
                    findNavController().navigate(R.id.action_profileFragment_to_movieDetailsFragment,data)
                }
            }
        }
        favoritesAdapter.setOnItemClickListener {
            val currentList = viewModel.favoriteMovies.value ?: mutableListOf()
            var movie : FavoriteData? = null
            for (i in currentList){
                if (i.movieImg == it){
                    movie = i
                    break
                }
            }
            val data = Bundle().apply {
                putSerializable("movieFavoriteData",movie)
            }
            findNavController().navigate(R.id.action_profileFragment_to_movieDetailsFragment,data)
        }
        binding.seeAllFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favouriteFragment)
        }
        binding.seeAllRecently.setOnClickListener {

            val data = Bundle().apply {
                putString("dataType","watched")
            }

            findNavController().navigate(R.id.action_profileFragment_to_moviesCategoryFragment,data)
        }
        binding.moviesNumber.text = viewModel.top100Movies.value?.size.toString()
        binding.favouriteNumber.text = viewModel.favoriteMovies.value?.size.toString()
        binding.watchListNumber.text = viewModel.recentlyWatched.value?.size.toString()


    }

    private fun setupRecentlyRv(){
        recentlyAdapter = ProfileAdapter()
        binding.rvRecentlyWatched.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recentlyAdapter
            try {
                viewModel.recentlyWatched.observe(requireActivity()){
                    if (it.isEmpty()){
                        binding.rvRecentlyWatched.visibility = View.GONE
                        binding.seeAllRecently.visibility = View.GONE
                        binding.emptyRecentlyTv.visibility = View.VISIBLE
                    }else{
                        binding.rvRecentlyWatched.visibility = View.VISIBLE
                        binding.seeAllRecently.visibility = View.VISIBLE
                        binding.emptyRecentlyTv.visibility = View.GONE
                        recentlyAdapter.differ.submitList(it)
                    }
                }
            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun setupFavoritesRv(){
        favoritesAdapter = ProfileAdapter()

        binding.rvFavoritesMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = favoritesAdapter
            try {
                viewModel.favoriteMovies.observe(requireActivity()){
                    if (it.isEmpty()){
                        binding.rvFavoritesMovies.visibility = View.GONE
                        binding.seeAllFavorite.visibility = View.GONE
                        binding.emptyFavoriteTv.visibility = View.VISIBLE
                    }else{
                        binding.rvFavoritesMovies.visibility = View.VISIBLE
                        binding.seeAllFavorite.visibility = View.VISIBLE
                        binding.emptyFavoriteTv.visibility = View.GONE
                        val list = mutableListOf<String>()
                        for(element in it){
                            list.add(element.movieImg)
                        }
                        favoritesAdapter.differ.submitList(list)
                    }

                }
            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}
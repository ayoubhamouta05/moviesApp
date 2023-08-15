package com.example.moviesapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.adapter.FavoriteAdapter
import com.example.moviesapp.databinding.FragmentFavouriteBinding
import com.example.moviesapp.model.favorite.FavoriteData
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var viewModel : MoviesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRv()

        favoriteAdapter.setOnItemClickListener {
            val data = Bundle().apply {
                putSerializable("movieFavoriteData",it)
            }
            findNavController().navigate(R.id.action_favouriteFragment_to_movieDetailsFragment,data)
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        favoriteAdapter.setOnLongItemClickListener {
            setupDeleteOption(it)
        }

    }

    private fun setupRv() {
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
            viewModel.favoriteMovies.observe(requireActivity()){
                favoriteAdapter.differ.submitList(it)
            }
        }
    }
    private fun setupDeleteOption(favoriteData : FavoriteData){
        val myDialog =AlertDialog.Builder(requireContext())
        myDialog.setMessage("Do You Delete This Movie From Favorite List ?")
        myDialog.setCancelable(true)
        myDialog.setPositiveButton("Cancel") {dialog,_->
            dialog.dismiss()
        }

        myDialog.setNegativeButton("Delete") {dialog,_->
            val currentList = viewModel.favoriteMovies.value ?: mutableListOf()
            currentList.remove(favoriteData)
            viewModel.favoriteMovies.postValue(currentList)
            dialog.dismiss()
        }
        myDialog.create()
        myDialog.show()
    }

}
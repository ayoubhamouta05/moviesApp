package com.example.moviesapp.ui.fragments

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
import com.example.moviesapp.databinding.FragmentProfileBinding
import com.example.moviesapp.model.FavoriteData


class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()

        favoriteAdapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_favouriteFragment_to_movieDetailsFragment)
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupRv(){
        var list = arrayListOf(FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
FavoriteData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "Fast And Furious 9",
            "descreption :  \n\n\n\n\n ",
            arrayListOf("drama","action","comedy","romantique"),
            "8.4"
        ),
        )
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
            favoriteAdapter.differ.submitList(list)
        }
    }
}
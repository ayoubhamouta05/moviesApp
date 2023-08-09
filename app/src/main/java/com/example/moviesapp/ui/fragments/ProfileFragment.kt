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


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var recentlyAdapter : ProfileAdapter
    private lateinit var favoritesAdapter : ProfileAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo : add button to swipe up the user information and do some animation
        setupRecentlyRv()
        setupFavoritesRv()

        recentlyAdapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_movieDetailsFragment)
        }
        favoritesAdapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_movieDetailsFragment)
        }

    }

    private fun setupRecentlyRv(){
        recentlyAdapter = ProfileAdapter()

        val list = arrayListOf(
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc="
        )
        binding.rvRecentlyWatched.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recentlyAdapter
            try {
                recentlyAdapter.differ.submitList(list)
            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun setupFavoritesRv(){
        favoritesAdapter = ProfileAdapter()

        val list = arrayListOf(
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc="
        )
        binding.rvFavoritesMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = favoritesAdapter
            try {
                favoritesAdapter.differ.submitList(list)
            } catch (ex: Exception) {
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}
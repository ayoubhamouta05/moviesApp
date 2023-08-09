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
import com.example.moviesapp.adapter.UpcomingAdapter
import com.example.moviesapp.databinding.FragmentMovieDetailsBinding


class MovieDetailsFragment : Fragment() {

    private lateinit var binding : FragmentMovieDetailsBinding
    lateinit var recommendedAdapter : UpcomingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUpcomingRv()

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }


    }


    private fun setupUpcomingRv(){
        recommendedAdapter = UpcomingAdapter()

        val list = arrayListOf("https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=")
        binding.rvRecommendedMovies.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL , false)
            adapter = recommendedAdapter
            try {
                recommendedAdapter.differ.submitList(list)
            }catch (ex : Exception){
                Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}
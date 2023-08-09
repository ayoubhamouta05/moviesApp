package com.example.moviesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.R
import com.example.moviesapp.adapter.NewMovieAdapter
import com.example.moviesapp.adapter.UpcomingAdapter
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.databinding.FragmentProfileBinding
import com.example.moviesapp.model.NewMoviesData
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var upcomingAdapter: UpcomingAdapter
    lateinit var newMovieAdapter: NewMovieAdapter

    private lateinit var viewModel : MoviesViewModel

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
        setupNewMovieRv()

        newMovieAdapter.setOnItemClickListener {
            // todo : add fragment that show the detail of this movie
            findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment)
        }
        upcomingAdapter.setOnItemClickListener {
            // todo : add fragment that show the detail of this movie
            findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment)
        }

        binding.seeAllUpcomingMovie.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_moviesCategoryFragment)
        }
    }

    private fun setupUpcomingRv() {
        upcomingAdapter = UpcomingAdapter()

        val list = arrayListOf<String>()
        viewModel.upcomingMovies.observe(requireActivity()){
            for(i in 0 until it.message[0].entries.size){
                list.add(it.message[0].entries[i].imageModel.url)
            }
            binding.rvUpcomingMovies.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingAdapter
                try {
                    viewModel.upcomingMovies.observe(requireActivity()){
                        upcomingAdapter.differ.submitList(list)
                    }

                } catch (ex: Exception) {
                    Toast.makeText(requireContext(), ex.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

//        val list = arrayListOf(
//            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
//            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
//            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
//            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
//            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
//            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
//            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
//            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
//            "https://media.istockphoto.com/id/847321716/fr/photo/macro-a-tir%C3%A9-de-l%C3%A9cran-de-lordinateur-avec-la-barre-dadresse-de-http-et-navigateur-web.webp?b=1&s=612x612&w=0&k=20&c=E8X_zwBZ-pFjMmah-in71Z_kqTmogNhDsPjpXrJow5g=",
//            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc="
//        )

    }

    private fun setupNewMovieRv() {
        newMovieAdapter = NewMovieAdapter()
        val list = arrayListOf(
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
        // todo : set it in try catch
        binding.rvNewMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = newMovieAdapter
            newMovieAdapter.differ.submitList(list)
        }
    }

}
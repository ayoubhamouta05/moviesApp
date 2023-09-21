package com.example.moviesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.adapter.CategoryListAdapter
import com.example.moviesapp.adapter.TopMovieAdapter
import com.example.moviesapp.adapter.UpcomingAdapter
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.ui.activities.MainActivity
import com.example.moviesapp.viewModel.MoviesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var upcomingAdapter: UpcomingAdapter
    private lateinit var topMovieAdapter: TopMovieAdapter
    private lateinit var viewModel: MoviesViewModel
    private lateinit var searchAdapter : CategoryListAdapter

    private var db = FirebaseDatabase.getInstance()
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel


        retrieveUserName()


        setupSearchOperation()

        viewModel.loadingUpcomingProgressBar.observe(requireActivity()) {
            if (it)
                binding.upcomingProgressBar.visibility = View.VISIBLE
            else
                binding.upcomingProgressBar.visibility = View.GONE
        }
        viewModel.loadingTopProgressBar.observe(requireActivity()) {
            if (it)
                binding.topProgressBar.visibility = View.VISIBLE
            else
                binding.topProgressBar.visibility = View.GONE
        }

        setupUpcomingRv()
        setupTopMovieRv()

        upcomingAdapter.setOnItemClickListener { upcomingMovieImg ->
            viewModel.upcomingMovies.observe(requireActivity()) {
                for (i in it) {
                    if (i.imageModel.url == upcomingMovieImg) {
                        val data = Bundle().apply {
                            putSerializable("movieUpcData", i)
                        }
                        findNavController().navigate(
                            R.id.action_homeFragment_to_movieDetailsFragment,
                            data
                        )
                        break
                    }
                }
            }
        }
        topMovieAdapter.setOnItemClickListener {
            val data = Bundle().apply {
                putSerializable(
                    "movieTopData", it
                )
            }
            findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment, data)
        }

        binding.seeAllUpcomingMovie.setOnClickListener {

            val bundle = Bundle().apply {
                putString("dataType", "Upcoming")
            }
            findNavController().navigate(R.id.action_homeFragment_to_moviesCategoryFragment, bundle)

        }

        binding.seeAllTopMovies.setOnClickListener {
            val bundle = Bundle().apply {
                putString("dataType", "Top")
            }
            findNavController().navigate(R.id.action_homeFragment_to_moviesCategoryFragment, bundle)
        }
    }

    private fun retrieveUserName() {
        val currentUser = auth.currentUser
        val reference = db.getReference("Users")

        reference.child(currentUser!!.email!!.replace(".com", ""))
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Retrieve the values from the dataSnapshot
                        snapshot.child("firstName").getValue(String::class.java)?.let {
                            binding.helloTv.text = "Hello $it"
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    retrieveUserName()
                }
            })


    }

    private fun setupUpcomingRv() {
        upcomingAdapter = UpcomingAdapter()

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

    private fun setupSearchOperation(){
        var job : Job? = null
        binding.searchEd.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                editable?.let {
                    if(editable.toString().isNotEmpty()){
                        binding.searchRv.visibility = View.VISIBLE
                        binding.moviesLayout.visibility = View.GONE
                        viewModel.getSearchMovie(editable.toString(),1)
                    }else{
                        binding.searchRv.visibility = View.GONE
                        binding.moviesLayout.visibility = View.VISIBLE
                    }
                }
            }
        }

        searchAdapter = CategoryListAdapter("Search")
        binding.searchRv.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = searchAdapter
            viewModel.moviesSearch.observe(requireActivity()){
                searchAdapter.differSearch.submitList(it)
            }
        }

        searchAdapter.setOnSearchItemClickListener {
            val data = Bundle().apply {
                putSerializable("movieSearchData",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment,data)
        }

    }

}
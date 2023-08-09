package com.example.moviesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.adapter.CategoryAdapter
import com.example.moviesapp.databinding.FragmentCategoryBinding
import com.example.moviesapp.databinding.FragmentFavouriteBinding
import com.example.moviesapp.model.CategoryData


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        WindowInsetsControllerCompat(requireActivity().window, view).apply {
            isAppearanceLightStatusBars = true
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        categoryAdapter.setOnItemClickListener {
            // todo : add fragment to show the list of all movie of that category name
            findNavController().navigate(R.id.action_categoryFragment_to_moviesCategoryFragment)
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }
    private fun setupRecyclerView(){
        var list = arrayListOf(CategoryData(
            "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
        "Action"),
            CategoryData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "Action"),
            CategoryData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "Action"),
            CategoryData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "Action"),
            CategoryData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "Action"),
            CategoryData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "Action"),
            CategoryData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "Action"),
            CategoryData(
                "https://media.istockphoto.com/id/503426092/fr/photo/page-web-%C3%A0.webp?b=1&s=612x612&w=0&k=20&c=qMRopRsx51jygWENCJSbV5VtIXBaeEUsuIx44dsEuKc=",
                "Action"),)
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter
            categoryAdapter.differ.submitList(list)
        }
    }
}
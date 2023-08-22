package com.example.moviesapp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.MoviesRepository

class MoviesViewModelProvider(
    val app: Application,
    private val moviesRepo: MoviesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(app, moviesRepo) as T
    }
}
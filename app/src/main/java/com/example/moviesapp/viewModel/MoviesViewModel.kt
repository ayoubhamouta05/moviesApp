package com.example.moviesapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.model.upcomingmovies.UpcomingMoviesData
import com.example.moviesapp.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(app:Application,
private val moviesRepo : MoviesRepository
) : AndroidViewModel(app){

    init {
        getUpcomingMovies()
    }

    var upcomingMovies  = MutableLiveData<UpcomingMoviesData>()

    fun getUpcomingMovies() = viewModelScope.launch {
        try {
            val response = moviesRepo.getUpcomingMovies()
            if (response.isSuccessful){
                upcomingMovies.postValue(response.body())
                Log.d("UpcomingMovies", response.body().toString())
            }else{
                Log.d("UpcomingMovies","failed : ${response.errorBody()}")
            }
        }catch (ex : Exception){
            Log.d("UpcomingMovies","exception : ${ex.message}")
        }
    }

}
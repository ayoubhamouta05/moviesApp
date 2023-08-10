package com.example.moviesapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.model.upcomingmovies.Entry
import com.example.moviesapp.model.upcomingmovies.UpcomingMoviesData
import com.example.moviesapp.repository.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(app:Application,
private val moviesRepo : MoviesRepository
) : AndroidViewModel(app){

    init {
        getUpcomingMovies()
    }

    var upcomingMovies  = MutableLiveData<List<Entry>>()

    private fun getUpcomingMovies() = viewModelScope.launch {
        try {
            val response = moviesRepo.getUpcomingMovies()
            if (response.isSuccessful){
                upcomingMovies.postValue(response.body()?.let {
                    it.message[0].entries
                    //todo: if the entries list size is less than 5 pass to the message[1] and so on
                })
                Log.d("UpcomingMovies", response.body()!!.message[0].entries.size.toString())
            }else{
                Log.d("UpcomingMovies","failed : ${response.errorBody()}")
            }
        }catch (ex : Exception){
            Log.d("UpcomingMovies","exception : ${ex.message}")
        }
    }

}
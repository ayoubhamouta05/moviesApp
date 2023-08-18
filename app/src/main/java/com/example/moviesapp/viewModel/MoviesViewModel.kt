package com.example.moviesapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.model.favorite.FavoriteData
import com.example.moviesapp.model.movieOfCategory.Result
import com.example.moviesapp.model.topMovies.TopMoviesData
import com.example.moviesapp.model.upcomingmovies.Entry
import com.example.moviesapp.repository.MoviesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class MoviesViewModel(
    app: Application,
    private val moviesRepo: MoviesRepository
) : AndroidViewModel(app) {
    var auth = FirebaseAuth.getInstance()
    var db = FirebaseDatabase.getInstance()

    var upcomingMovies = MutableLiveData<List<Entry>>()

    var top100Movies = MutableLiveData<List<TopMoviesData>>()

    var moviesOfCategory = MutableLiveData<List<Result>>()

    var favoriteMovies = MutableLiveData(mutableListOf<FavoriteData>())

    var recentlyWatched = MutableLiveData(mutableListOf<String>())

    var loadingUpcomingProgressBar = MutableLiveData(false)
    var loadingTopProgressBar = MutableLiveData(false)
    var loadingCategoryProgressBar = MutableLiveData(false)

    init {
        getUpcomingMovies()
        getTop100Movies()
    }
    private fun getUpcomingMovies() = viewModelScope.launch {

        try {
            loadingUpcomingProgressBar.postValue(true)
            val response = moviesRepo.getUpcomingMovies()
            if (response.isSuccessful) {
                upcomingMovies.postValue(response.body()?.let {
                    it.message[0].entries
                    //todo: if the entries list size is less than 5 pass to the message[1] and so on
                })

                loadingUpcomingProgressBar.postValue(false)
            } else {
                Log.d("UpcomingMovies", "failed : ${response.errorBody()}")
                // todo : handle the failed response

                loadingUpcomingProgressBar.postValue(false)
            }
        } catch (ex: Exception) {
            Log.d("UpcomingMovies", "exception : ${ex.message}")
            loadingUpcomingProgressBar.postValue(false)
        }

    }

    private fun getTop100Movies() = viewModelScope.launch {
        try {
            loadingTopProgressBar.postValue(true)
            val response = moviesRepo.getTopMovies()
            if (response.isSuccessful) {
                top100Movies.postValue(response.body())
                Log.d("TopMovies", response.body()!!.size.toString())
                loadingTopProgressBar.postValue(false)
            } else {
                //todo : handel the failed response
                loadingTopProgressBar.postValue(false)
            }
        } catch (ex: Exception) {
            Log.d("TopMovies", "exception : ${ex.message}")
            loadingTopProgressBar.postValue(false)
        }
    }

    fun getMoviesWithCategory(category: String) = viewModelScope.launch {
        var id = 28
        when (category) {
            "Action" -> {
                id = 28
            }

            "Adventure" -> {
                id = 12
            }

            "Animation" -> {
                id = 16
            }

            "Documentary" -> {
                id = 99
            }

            "Comedy" -> {
                id = 35
            }

            "Drama" -> {
                id = 18
            }

            "Family" -> {
                id = 10751
            }

            "Fantasy" -> {
                id = 14
            }
        }
        try {
            loadingCategoryProgressBar.postValue(true)
            val response = moviesRepo.getMoviesWithCategory(id)
            if (response.isSuccessful) {
                moviesOfCategory.postValue(response.body()!!.results)
                Log.d("MoviesWithCategory", response.body()!!.results.size.toString())
                loadingCategoryProgressBar.postValue(false)
            } else {
                // todo : handle the failed response
                loadingCategoryProgressBar.postValue(false)
            }
        } catch (ex: Exception) {
            Log.d("MoviesWithCategory", "exception : ${ex.message}")
            loadingCategoryProgressBar.postValue(false)
        }
    }

    fun getRecentlyWatched(){
        val currentUser = auth.currentUser
        val reference = db.getReference("Recently")
        reference.child(currentUser?.email!!.replace(".com","")).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun setRecentlyWatched(movie : String , list : MutableList<String>){
        list.add(movie)
        recentlyWatched.postValue(list)
    }




}
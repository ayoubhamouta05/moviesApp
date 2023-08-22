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
        getFavoriteMoviesOFUser()
        getWatchedMoviesOFUser()
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

    // get the movies and set it all in the favoriteMovies Value LiveData
    private fun getFavoriteMoviesOFUser() {
        val reference = db.getReference("Movies/Favorite")
        reference.child(auth.currentUser?.email!!.replace(".com", ""))
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val list = mutableListOf<FavoriteData>()
                        for (childSnapshot in snapshot.children) {
                            val data = childSnapshot.getValue(FavoriteData::class.java)
                            data?.let {
                                list.add(data)
                            }
                        }
                        favoriteMovies.postValue(list)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }

    fun setMoviesAsFavorite(movie: FavoriteData?) {

        val currentList = favoriteMovies.value ?: mutableListOf()
        movie?.let {
            val reference =
                db.getReference("Movies/Favorite/${auth.currentUser?.email!!.replace(".com", "")}")

            // Generate a unique key for the new movie
            val newKey = reference.push().key

            newKey?.let { newKey ->
                reference.child(newKey).setValue(movie)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            currentList.add(movie)
                        } else {
                            // Handle the error if the operation fails
                        }
                    }
            }

            favoriteMovies.postValue(currentList)
        }

    }

    fun deleteMovieFromFavorite(
        name: String, description: String, genres: List<String>
    ) {
        val reference = db.getReference("Movies/Favorite")
        val list = favoriteMovies.value ?: mutableListOf()
        reference.child(auth.currentUser?.email!!.replace(".com", ""))
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(FavoriteData::class.java)
                        data?.let {
                            if (it.movieGenre == genres && it.movieDescription == description && it.movieName == name) {
                                childSnapshot.ref.removeValue().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val itemToRemove = list.find { favorite -> favorite == it }
                                        itemToRemove?.let { item ->
                                            list.remove(item)
                                            favoriteMovies.postValue(list)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


    }

    private fun getWatchedMoviesOFUser() {
        val reference = db.getReference("Movies/Recently")
        reference.child(auth.currentUser?.email!!.replace(".com", ""))
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val list = mutableListOf<String>()
                        for (childSnapshot in snapshot.children) {
                            val data = childSnapshot.getValue(String::class.java)
                            data?.let {
                                list.add(data)
                            }
                        }
                        recentlyWatched.postValue(list)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun setMoviesAsWatched(imageUrl: String) {
        val currentList = recentlyWatched.value ?: mutableListOf()

        val reference =
            db.getReference("Movies/Recently/${auth.currentUser?.email!!.replace(".com", "")}")

        // Generate a unique key for the new movie
        val newKey = reference.push().key

        newKey?.let { newKey ->
            reference.child(newKey).setValue(imageUrl)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        currentList.add(imageUrl)
                    } else {
                        // Handle the error if the operation fails
                    }
                }
        }
        recentlyWatched.postValue(currentList)

    }

    fun deleteMoviesFromRecently(name: String, description: String) {
        val reference = db.getReference("Movies/Recently")
        val currentList = recentlyWatched.value ?: mutableListOf()
        reference.child(auth.currentUser?.email!!.replace(".com", ""))
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(String::class.java)
                        data?.let { imageUrl ->
                            val list = top100Movies.value ?: mutableListOf()
                            for (i in list) {
                                if (i.title == name && i.description == description) {
                                    if (imageUrl == i.image) {
                                        childSnapshot.ref.removeValue().addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                currentList.remove(i.image)
                                                recentlyWatched.postValue(currentList)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }


}
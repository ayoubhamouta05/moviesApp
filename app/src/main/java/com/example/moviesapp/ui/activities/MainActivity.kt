package com.example.moviesapp.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.graphics.green
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.repository.MoviesRepository
import com.example.moviesapp.viewModel.MoviesViewModel
import com.example.moviesapp.viewModel.MoviesViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var viewModel: MoviesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller?.hide(WindowInsets.Type.systemBars())
            controller?.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            // Fallback for devices before Android 11
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.movies_navHost_Fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavigation.setupWithNavController(navController)

        val moviesRepository = MoviesRepository()
        val viewModelFactory = MoviesViewModelProvider(application, moviesRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]

        setupBackButton(navHostFragment)


        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            navController = navHostFragment.navController
            val currentDestination = navController.currentDestination?.id

            // Check if the selected item is different from the current destination
            if (currentDestination != menuItem.itemId) {
                // Clear the back stack before navigating
                navController.popBackStack(navController.graph.startDestinationId, false)

                // Navigate to the selected destination

                navController.navigate(menuItem.itemId)
            }
            true
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBackButton(navHostFragment: NavHostFragment) {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            private var backPressedTimestamp: Long = 0

            override fun handleOnBackPressed() {
                val currentTimestamp = System.currentTimeMillis()
                val elapsedMillis = currentTimestamp - backPressedTimestamp

                navController = navHostFragment.navController
                val currentDestination = navController.currentDestination?.id

                // Check if the selected item is different from the current destination
                if (currentDestination == R.id.homeFragment) {

                    if (elapsedMillis < 5000) {
                        // Finish the activity
                        finishAffinity()
                    } else {
                        backPressedTimestamp = currentTimestamp
                        Toast.makeText(
                            this@MainActivity,
                            "Press back again to exit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    navController.popBackStack()
                }

            }

        })

    }


}
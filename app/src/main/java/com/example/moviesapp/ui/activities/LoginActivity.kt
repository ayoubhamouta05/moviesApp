package com.example.moviesapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (auth.currentUser != null) {
            auth.signOut()
        }

        binding.loginBtn.setOnClickListener {
            setupLogin(binding.emailEd.text.toString(), binding.passwordEd.text.toString())
        }

        binding.signUpTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun setupLogin(email: String, password: String) {
        if (email.isEmpty()) {
            binding.emailEd.error = "Email Is Empty !"
        } else if (password.isEmpty()) {
            binding.passwordEd.error = "Password Is Empty !"
        } else {
            showProgressBar(true)
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (auth.currentUser!!.isEmailVerified) {
                            checkUserState()
                            showProgressBar(false)
                            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                        } else {
                            auth.currentUser!!.sendEmailVerification()
                            showProgressBar(false)
                            auth.signOut()
                            Snackbar.make(
                                binding.root,
                                "Please verify Your email \n(check your spam email if you can't find our email)",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        showProgressBar(false)
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Log in error")
                        alertDialog.setCancelable(false)
                        alertDialog.setMessage("wrong credential check your email and your password again")
                        alertDialog.setPositiveButton("TRY AGAIN") { dialog, _ ->
                            binding.emailEd.setText("")
                            binding.passwordEd.setText("")
                            dialog.dismiss()
                        }
                        alertDialog.show()
                    }

                }
            } catch (ex: Exception) {
                showProgressBar(false)
                // Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showProgressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else
            binding.progressBar.visibility = View.GONE
    }

    private fun checkUserState() {
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}
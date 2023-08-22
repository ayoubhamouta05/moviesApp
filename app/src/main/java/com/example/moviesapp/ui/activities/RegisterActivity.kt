package com.example.moviesapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapp.databinding.ActivityRegisterBinding
import com.example.moviesapp.model.user.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private var db = FirebaseDatabase.getInstance()
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


        binding.signUpBtn.setOnClickListener {
            registerNewUser(
                binding.firstNameEd.text.toString(),
                binding.lastNameEd.text.toString(),
                binding.emailEd.text.toString(),
                binding.passwordEd.text.toString(),
                binding.confirmPasswordEd.text.toString()
            )
        }
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        binding.signInTv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerNewUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (firstName.isEmpty()) {
            binding.firstNameEd.error = "First Name Is Empty"
        } else if (lastName.isEmpty()) {
            binding.lastNameEd.error = "Last Name Is Empty"
        } else if (email.isEmpty()) {
            binding.emailEd.error = "Email Is Empty"
        } else if (password.isEmpty()) {
            binding.passwordEd.error = "Password Is Empty"
        } else if (confirmPassword.isEmpty()) {
            binding.confirmPasswordEd.error = "Confirm Password Is Empty"
        } else {
            showProgressBar(true)
            if (password != confirmPassword) {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Sign Up Error")
                alertDialog.setMessage("wrong credential confirmed Password and Password are not the same")
                showProgressBar(false)
                alertDialog.setNegativeButton("CANCEL") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                alertDialog.setPositiveButton("TRY AGAIN") { dialog, _ ->
                    binding.confirmPasswordEd.setText("")
                    dialog.dismiss()
                }
                alertDialog.show()
            } else {
                try {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val reference = db.getReference("Users")
                                reference.child(email.replace(".com", "")).setValue(
                                    UserData(
                                        firstName, lastName, email, password
                                    )
                                )

                                startActivity(Intent(this, LoginActivity::class.java))
                                showProgressBar(false)
                                Toast.makeText(
                                    this,
                                    "User Registered Successfully\nPlease Verify Your email Before Login",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                auth.currentUser!!.sendEmailVerification()
                            } else {
                                showProgressBar(false)
                                Toast.makeText(
                                    this,
                                    task.exception?.message.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                            }
                        }
                } catch (ex: Exception) {
                    showProgressBar(false)
                    //  Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showProgressBar(state: Boolean) {
        if (state)
            binding.progressBar.visibility = View.VISIBLE
        else
            binding.progressBar.visibility = View.GONE
    }


}
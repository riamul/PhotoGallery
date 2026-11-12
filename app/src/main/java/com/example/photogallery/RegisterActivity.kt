package com.example.photogallery

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {

    private lateinit var user: EditText
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var confirm: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize EditText fields
        user = findViewById(R.id.Name)
        email = findViewById(R.id.Email)
        pass = findViewById(R.id.password)
        confirm = findViewById(R.id.confirm_password)
    }

    fun startFeed(view: View) {

        val sUser = user.text.toString().trim()
        val sEmail = email.text.toString().trim()
        val sPass = pass.text.toString()
        val sConfirm = confirm.text.toString()

        // Check empty fields
        if (sUser.isEmpty() || sEmail.isEmpty() ||
            sPass.isEmpty() || sConfirm.isEmpty()
        ) {
            Toast.makeText(
                this,
                "Please fill all fields.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Check password length
        if (sPass.length < 6) {
            Toast.makeText(
                this,
                "Password must be at least 6 characters.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Check password confirmation
        if (sPass != sConfirm) {
            Toast.makeText(
                this,
                "Password doesn't match.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Check whether username already exists
        val database = FirebaseDatabase.getInstance()
            .getReference("users")

        database.orderByValue()
            .equalTo(sUser)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Username already exists.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    // Create Firebase account
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(sEmail, sPass)
                        .addOnSuccessListener { result ->

                            val uid = result.user?.uid

                            if (uid != null) {

                                // Save username in Firebase Database
                                database.child(uid)
                                    .setValue(sUser)
                                    .addOnSuccessListener {

                                        Toast.makeText(
                                            this@RegisterActivity,
                                            "Registration successful!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        // Navigate to FeedActivity
                                        startActivity(
                                            Intent(
                                                this@RegisterActivity,
                                                LoginActivity::class.java
                                            )
                                        )

                                        finish()
                                    }
                                    .addOnFailureListener { error ->
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            error.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                        .addOnFailureListener { error ->
                            Toast.makeText(
                                this@RegisterActivity,
                                error.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@RegisterActivity,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    // Navigate to LoginActivity
    fun loginUser(view: View) {
        startActivity(
                Intent(this, LoginActivity::class.java)
                )
    }
}
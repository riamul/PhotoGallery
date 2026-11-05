package com.example.photogallery

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    lateinit var user: EditText
    lateinit var email:EditText
    lateinit var pass: EditText
    lateinit var confirm: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        user =findViewById< EditText>(R.id.Name)
        user =findViewById< EditText>(R.id.Email)
        user =findViewById< EditText>(R.id.password)
        user =findViewById< EditText>(R.id.confirm_password)

    }

    fun startFeed(view: View) {}
    fun loginUser(view: View) {
        startActivity(Intent((this@RegisterActivity,LoginActivity::class.java)

    }

}
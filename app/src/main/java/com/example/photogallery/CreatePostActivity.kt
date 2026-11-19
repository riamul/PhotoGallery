package com.example.photogallery

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreatePostActivity : AppCompatActivity() {
    lateinit var photo: ImageView
    lateinit var caption: EditText
    lateinit var count : TextView
    lateinit var iid: String
    lateinit var iusername: String
    lateinit var iemail: String



    private val cloudName ="ea65walv"
    private val uploadPreset ="PhotosGallery"

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()){
            uri: Uri? ->
            if(uri != null){
                imageUri = uri
                photo =findViewById<ImageView>(R.id.photo)
                photo .setImageURI(uri)
            }
        }

    var imageUri : Uri?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

    }

    fun selectPhoto(view: View){
       galleryLauncher.launch("image/*")}

    fun createPost(view: View) {

        iid = intent.getStringExtra("Id").toString()
        iusername = intent.getStringExtra("Username").toString()
        iemail = intent.getStringExtra("Email").toString()

        if (imageUri != null) {
            Toast.makeText(
                this@CreatePostActivity,
                "PlS select an image.",
                Toast.LENGTH_SHORT

            ).show()



            return
        }
        if (caption.text.toString().length > 100) {
            Toast.makeText(
                this@CreatePostActivity,
                "caption is too long.Need to short.",
                Toast.LENGTH_SHORT

            ).show()
            return

        }
        uploadImage()

    }

    private fun uploadImage() {
        TODO("Not yet implemented")
    }
}
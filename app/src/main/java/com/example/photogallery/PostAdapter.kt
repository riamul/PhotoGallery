package com.example.photogallery

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class PostAdapter(private val posts: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    class PostViewHolder (view : View): RecyclerView.ViewHolder(view){
        val profile: TextView =view.findViewById(R.id.profile)
        val username: TextView =view.findViewById(R.id.user)
        val time: TextView =view.findViewById(R.id.time)
        val photo: ImageView =view.findViewById(R.id.photo)
        val caption: TextView =view.findViewById(R.id.caption)

     }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostAdapter.PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feed, parent,false)
        return PostViewHolder(view)


    }

    override fun onBindViewHolder(holder: PostAdapter.PostViewHolder, position: Int) {
        val post = posts[position]
        holder.profile.text = post.username.first().uppercase()
        holder.username.text = post.username
        holder.time.text = post.time
        holder.caption.text= post.caption



        Thread {
            try {
                val url = URL(post.photo)
                val connection = url.openConnection()as HttpURLConnection
                connection.connect()
                val bitmap = BitmapFactory.decodeStream(connection.inputStream)

                holder.photo.post {
                    holder.photo.setImageBitmap(bitmap)
                }

                connection.disconnect()
            }catch (e: Exception){
                e.printStackTrace()
            }


        }.start()
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

package com.postme

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(RetrofitAPI::class.java)

        service.getPosts().enqueue(object: Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                t.message?.let {
                    println(it)
                } ?: Toast.makeText(this@MainActivity, "Unknown error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                response.body()?.let {
                    val userId = it.userId
                    val id = it.id
                    val title = it.title
                    val body = it.body
                } ?: Toast.makeText(this@MainActivity, "Body is null", Toast.LENGTH_SHORT).show()
            }
        })

    }
}

interface RetrofitAPI {
    @GET("posts/1")
    fun getPosts() : Call<Post>
}

data class Post(
        var userId: Int,
        var id: Int,
        var title : String,
        var body : String
)
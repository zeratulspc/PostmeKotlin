package com.postme

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("posts/1")
    fun getPosts() : Call<Post>
}

class RetrofitService {

    fun retroInit() {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitAPI::class.java)

        var result : Post = Post(0,0,"","")
        service.getPosts().enqueue(object: Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                t.message?.let {
                    println(it)
                } ?: println("Unknown error")
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                response.body()?.let {
                    result.userId = it.userId
                    result.id = it.id
                    result.title = it.title
                    result.body = it.body
                    println("늦늦")
                    println(result.body)
                    println("늦늦")
                } ?: println("Body is null")
            }
        })
    }

}
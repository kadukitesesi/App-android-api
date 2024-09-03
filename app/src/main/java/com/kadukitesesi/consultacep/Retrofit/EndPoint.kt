package com.kadukitesesi.consultacep.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoint {

    @GET("posts")
    fun getPosts(): Call<List<Posts>>

    @GET("posts/{id}")
    fun getPostsById(@Path("id") id: Int): Call<Posts>

}
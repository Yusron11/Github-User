package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.BuildConfig
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.GithubUserResponse
import com.dicoding.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers ("Authorization: token " + BuildConfig.TOKEN)
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<GithubUserResponse>

    @GET("users/{username}")
    @Headers ("Authorization: token " + BuildConfig.TOKEN)
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers ("Authorization: token " + BuildConfig.TOKEN)
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers ("Authorization: token " + BuildConfig.TOKEN)
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

}
package com.dicoding.githubuser.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val followers = MutableLiveData<List<ItemsItem>>()
    private val following = MutableLiveData<List<ItemsItem>>()

    val followersLive: LiveData<List<ItemsItem>> = followers
    val followingLive: LiveData<List<ItemsItem>> = following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setFollower (username: String) {
        _isLoading.value = true

        val call = ApiConfig.getApiService().getFollowers(username)
        call.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    followers.value = response.body()

                } else {

                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun setFollowing (username: String) {
        _isLoading.value = true

        val call = ApiConfig.getApiService().getFollowing(username)
        call.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    following.value = response.body()

                } else {

                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

}
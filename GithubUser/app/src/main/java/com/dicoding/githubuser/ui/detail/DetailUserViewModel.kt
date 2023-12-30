package com.dicoding.githubuser.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.database.Favorite
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.repository.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val favRepository: FavoriteRepository = FavoriteRepository(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detail = MutableLiveData<DetailUserResponse>()
    val detail: LiveData<DetailUserResponse> = _detail

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setDetailUser(username: String) {
        _isLoading.value = true

        val call = ApiConfig.getApiService().getDetailUser(username)
        call.enqueue(object : Callback<DetailUserResponse?> {
            override fun onResponse(
                call: Call<DetailUserResponse?>,
                response: Response<DetailUserResponse?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detail.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailUserResponse?>, t: Throwable) {
                _isLoading.value = false
            }
        })

        favRepository.getFavoriteUserByUsername(username).observeForever { favorite ->
            _isFavorite.value = favorite != null
        }
    }

    fun insert(username: String, avatarUrl: String) {
        val favorite = Favorite(username = username, avatarUrl = avatarUrl)
        favRepository.insert(favorite)
    }

    fun delete(username: String, avatarUrl: String) {
        val favorite = Favorite(username = username, avatarUrl = avatarUrl)
        favRepository.delete(favorite)
    }
}

// MainViewModel.kt
package com.dicoding.githubuser.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuser.data.response.GithubUserResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.ui.setting.SettingPreferences
import com.dicoding.githubuser.ui.setting.SettingViewModel
import com.dicoding.githubuser.ui.setting.dataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val settingViewModel: SettingViewModel by lazy {
        SettingViewModel(SettingPreferences.getInstance(application.dataStore))
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return settingViewModel.getThemeSettings()
    }

    fun searchUsers(query: String) {
        _isLoading.value = true

        val call = ApiConfig.getApiService().getSearchUsers(query)
        call.enqueue(object : Callback<GithubUserResponse?> {
            override fun onResponse(
                call: Call<GithubUserResponse?>,
                response: Response<GithubUserResponse?>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val githubUserResponse = response.body()
                    val usersList = githubUserResponse?.items ?: emptyList()
                    _users.value = usersList
                } else {
                }
            }

            override fun onFailure(call: Call<GithubUserResponse?>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    init {
        searchUsers("Yusron1")
    }
}

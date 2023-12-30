package com.dicoding.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.githubuser.ui.detail.DetailUserActivity
import com.dicoding.githubuser.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        supportActionBar?.elevation = 0f

        favoriteViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application)).get(FavoriteViewModel::class.java)

        userAdapter = UserAdapter(this, emptyList())
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = userAdapter

        userAdapter.setOnItemClickListener { user ->
            val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
            intent.putExtra("username", user.login)
            startActivity(intent)
        }

        favoriteViewModel.getAllFavorite().observe(this) { favorites ->
            val items = arrayListOf<ItemsItem>()
            favorites.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            userAdapter.submitList(items)
        }
    }
}

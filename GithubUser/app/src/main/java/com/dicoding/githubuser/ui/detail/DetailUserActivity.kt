package com.dicoding.githubuser.ui.detail

import ViewModelFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.database.Favorite
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.ui.follow.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private var checked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra("username")
        if (username != null) {
            detailUserViewModel = obtainViewModel(this@DetailUserActivity)
            detailUserViewModel.setDetailUser(username)
        }

        detailUserViewModel.detail.observe(this, Observer {
            displayDetailUser(it)
        })

        detailUserViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        detailUserViewModel.isFavorite.observe(this, {
            checked = it
            updateFavoriteButtonIcon()
        })

        val adapter = ViewPagerAdapter(this)
        adapter.username = intent.getStringExtra("username") ?: ""

        binding.viewPager.adapter = adapter
        val tabLayout = binding.tabs

        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }
        }.attach()

        binding.fav.setOnClickListener {
            val username = intent.getStringExtra("username")
            val avatarUrl = detailUserViewModel.detail.value?.avatarUrl
            checked = !checked
            if (checked) {
                if (username != null && avatarUrl != null) {
                    detailUserViewModel.insert(username, avatarUrl)
                }
            } else {
                if (username != null && avatarUrl != null) {
                    detailUserViewModel.delete(username, avatarUrl)
                }
            }
            updateFavoriteButtonIcon()
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun displayDetailUser(detail: DetailUserResponse) {
        binding.apply {
            tvName.text = detail.name
            tvUsername.text = detail.login
            tvBio.text = detail.bio?.toString()
            tvFollow.text = "${detail.followers} Followers     ${detail.following} Following"
        }
        Glide.with(this)
            .load(detail.avatarUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(binding.imgUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun updateFavoriteButtonIcon() {
        binding.fav.setImageResource(if (checked) R.drawable.ic_favored else R.drawable.ic_favorite)
    }
}

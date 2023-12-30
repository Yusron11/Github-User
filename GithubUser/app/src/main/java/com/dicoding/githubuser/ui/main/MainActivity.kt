package com.dicoding.githubuser.ui.main


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.ui.detail.DetailUserActivity
import com.dicoding.githubuser.ui.favorite.FavoriteActivity
import com.dicoding.githubuser.ui.setting.SettingActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        userAdapter = UserAdapter(this, ArrayList())
        binding.rvUser.adapter = userAdapter

        viewModel.users.observe(this, Observer {
            displayUsers(it)
        })

        viewModel.isLoading.observe(this, Observer {
            showLoading(it)
        })

        viewModel.getThemeSettings().observe(this, Observer { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val query = s.toString()
                    searchBar.setText(query)
                    viewModel.searchUsers(query)

                }

                override fun afterTextChanged(s: Editable?) {

                }
            })

            searchView.editText?.setOnEditorActionListener { v, actionId, event ->
                searchView.hide()
                return@setOnEditorActionListener true
            }
        }

        userAdapter.setOnItemClickListener { user ->
            val intent = Intent(this, DetailUserActivity::class.java)
            intent.putExtra("username", user.login)
            startActivity(intent)
        }

    }

    private fun displayUsers(users: List<ItemsItem>) {
        userAdapter.submitList(users)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}

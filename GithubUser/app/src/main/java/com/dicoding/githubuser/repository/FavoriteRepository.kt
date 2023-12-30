package com.dicoding.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.data.database.Favorite
import com.dicoding.githubuser.data.database.FavoriteDao
import com.dicoding.githubuser.data.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getFavoriteUserByUsername(username: String): LiveData<Favorite> =
        mFavoritesDao.getFavoriteUserByUsername(username)

    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoritesDao.getAllFavorite()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoritesDao.insert(favorite) }
    }

    fun delete(favorite: Favorite) {
        executorService.execute { mFavoritesDao.delete(favorite) }
    }

}
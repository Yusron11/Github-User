package com.dicoding.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM Favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<Favorite>

    @Query("SELECT * from Favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>

}
package com.dicoding.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githubuser.data.database.Favorite

class FavDiffCallback(private val oldFavList: List<Favorite>, private val newFavList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size
    override fun getNewListSize(): Int = newFavList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].username == newFavList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFav = oldFavList[oldItemPosition]
        val newFav = newFavList[newItemPosition]
        return oldFav.avatarUrl == newFav.avatarUrl
    }
}
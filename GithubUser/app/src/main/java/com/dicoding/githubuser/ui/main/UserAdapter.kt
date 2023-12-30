package com.dicoding.githubuser.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ItemUserBinding

class UserAdapter(private val context: Context, private val userList: List<ItemsItem>) :
    ListAdapter<ItemsItem, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    private lateinit var layoutBinding: ItemUserBinding

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usernameTextView: TextView = layoutBinding.tvItemName
        val userImageView: ImageView = layoutBinding.imgItemPhoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        layoutBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(layoutBinding.root)
    }

    private var onItemClickListener: ((ItemsItem) -> Unit)? = null
    fun setOnItemClickListener(listener: (ItemsItem) -> Unit) {
        onItemClickListener = listener
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)

        holder.usernameTextView.text = user.login

        Glide.with(context)
            .load(user.avatarUrl)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(holder.userImageView)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(user)
        }
    }


    private class UserDiffCallback : DiffUtil.ItemCallback<ItemsItem>() {
        override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
            return oldItem == newItem
        }
    }

}

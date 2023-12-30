package com.dicoding.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class GithubUserResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem>? = null
)

data class ItemsItem(

	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)

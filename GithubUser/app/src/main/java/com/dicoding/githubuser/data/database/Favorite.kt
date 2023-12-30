package com.dicoding.githubuser.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null
): Parcelable


package com.androidrey.composenavigation.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey
    @Json(name = "id") val userId: String,
    @Json(name = "login") val userName: String,
    @Json(name = "node_id") val nodeId: String,
    @Json(name = "avatar_url") val userAvatar: String
) : Serializable
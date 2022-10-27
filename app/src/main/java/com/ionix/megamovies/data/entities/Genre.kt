package com.ionix.megamovies.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genres")
data class Genre (
	@PrimaryKey
	@SerializedName("key") val genreId : String,
	@SerializedName("value") val value : String
)
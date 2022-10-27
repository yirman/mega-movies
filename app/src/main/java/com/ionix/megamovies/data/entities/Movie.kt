package com.ionix.megamovies.data.entities

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date


@Entity(tableName = "movies")
data class Movie constructor(
	@PrimaryKey
	@SerializedName("id") var movieId : String = "",
	@SerializedName("title") var title : String? = null,
	@SerializedName("releaseState") var releaseState : Date? = null,
	@SerializedName("image") var image : String? = null,
	@SerializedName("plot") var plot : String? = null,
	@SerializedName("genres") var genres : String? = null,
	@SerializedName("directors") var directors : String? = null,
	@SerializedName("stars") var stars : String? = null,
	@SerializedName("genreList") @Ignore var genreList : List<Genre>? = null
)

@SuppressLint("SimpleDateFormat")
fun Movie.parseDate(): String?{
	return releaseState?.let{ SimpleDateFormat("dd MMM yyyy").format(it) }
}
package com.ionix.megamovies.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.ionix.megamovies.databinding.ActivityMoviePlayerBinding

class MoviePlayerActivity : AppCompatActivity() {

    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var videoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoviePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoUrl = intent.getStringExtra("video_url")

        videoUrl?.let{ videoUrl ->
            simpleExoPlayer = SimpleExoPlayer.Builder(binding.root.context).build()
            binding.playervideo.player = simpleExoPlayer
            val mediaItem = MediaItem.fromUri(videoUrl)
            simpleExoPlayer?.addMediaItem(mediaItem)
            simpleExoPlayer?.prepare()
            simpleExoPlayer?.playWhenReady = true;
        }
    }


    override fun onResume() {
        super.onResume()
        simpleExoPlayer?.playWhenReady = true
    }

    override fun onPause() {
        simpleExoPlayer?.playWhenReady = false
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer?.release()
        simpleExoPlayer = null
    }
}
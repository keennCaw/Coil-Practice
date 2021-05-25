package com.example.practicecoil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.fetch.VideoFrameFileFetcher
import coil.fetch.VideoFrameUriFetcher
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.example.practicecoil.databinding.ActivityVideoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private val videoUrl =
        "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        val view = binding.root

        GlobalScope.launch {
            loadVideoFrame(5000)
        }

        setContentView(view)
    }

    private suspend fun loadVideoFrame(timeMillis:Long){
        val imageLoader = ImageLoader.Builder(applicationContext)
            .componentRegistry {
                add(VideoFrameFileFetcher(applicationContext))
                add(VideoFrameUriFetcher(applicationContext))
                add(VideoFrameDecoder(applicationContext))
            }
            .build()
        val request = ImageRequest.Builder(applicationContext)
            .data(videoUrl)
            .target(binding.imageView)
            .videoFrameMillis(timeMillis)
            .build()

        imageLoader.execute(request)
    }

}
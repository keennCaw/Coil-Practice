package com.example.practicecoil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.practicecoil.databinding.ActivitySvgBinding

class SvgActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySvgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySvgBinding.inflate(layoutInflater)
        val view = binding.root
        val svgImgUrl = "https://cdn.shopify.com/s/files/1/0496/1029/files/Freesample.svg?5153"

        //add svg decoder to imageLoader
        val imageLoader = ImageLoader.Builder(applicationContext)
            .componentRegistry {
                add(SvgDecoder(applicationContext))
            }
            .build()

        //request
        val request = ImageRequest.Builder(applicationContext)
            .data(svgImgUrl)
            .transformations(CircleCropTransformation())
            .target(binding.imageView)
            .build()

        //run from background thread
        imageLoader.enqueue(request)


        setContentView(view)
    }
}
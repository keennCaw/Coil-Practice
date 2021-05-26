package com.example.practicecoil

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.practicecoil.databinding.ActivityGifBinding

class GifActivity : AppCompatActivity() {


    private lateinit var binding: ActivityGifBinding
    private val gifUrl = "https://media.giphy.com/media/BfbUe877N4xsUhpcPc/giphy.gif"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGifBinding.inflate(layoutInflater)
        val view = binding.root

        //construct ImageLoader with gif decoders
        //ImageDecoderDecoder is much faster and provides support for animated WebP and HEIF
        val imageLoader = ImageLoader.Builder(applicationContext)
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(applicationContext))
                } else {
                    add(GifDecoder())
                }
            }
            .memoryCachePolicy(CachePolicy.DISABLED) //Disable Cache
            .build()

        //sample request with error handling using target
        val request = ImageRequest.Builder(applicationContext)
            .data(gifUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_placeholder)
            .target(
                onStart = { placeholder ->
                    //do something when waiting for result
                    imageLoader.enqueue(placeHolderRequest())
                    showLoading()
                },
                onSuccess = { result ->
                    //do something on Success
                    imageLoader.enqueue(imageSuccessRequest(binding.imageView, result))
                    showImageResult()
                },
                onError = { error ->
                    //do something on Failure
                    showImageResult()
                    binding.imageView.setImageDrawable(error)
                })
            .build()
        imageLoader.enqueue(request)

        setContentView(view)
    }

    private fun placeHolderRequest():ImageRequest{
        return ImageRequest.Builder(applicationContext)
            .data(R.drawable.gif_loading)
            .target(binding.imageViewPlaceHolder)
            .build()
    }

    private fun imageSuccessRequest(imageView: ImageView, result: Drawable):ImageRequest{
        return ImageRequest.Builder(applicationContext)
            .data(result)
            .target(imageView)
            .build()
    }


    private fun showLoading(){
        binding.imageView.visibility = View.GONE
        binding.imageViewPlaceHolder.visibility = View.VISIBLE
    }

    private fun showImageResult(){
        binding.imageView.visibility = View.VISIBLE
        binding.imageViewPlaceHolder.visibility = View.GONE
    }
}
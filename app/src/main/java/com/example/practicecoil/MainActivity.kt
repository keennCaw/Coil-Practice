package com.example.practicecoil

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import coil.ImageLoader
import coil.load
import coil.metadata
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.practicecoil.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{
        const val imageUrl = "https://4.img-dpreview.com/files/p/E~TS590x0~articles/3925134721/0266554465.jpeg"
        //lateinit var bitmap: Bitmap
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        //Using a Memory Cache Key as a Placeholder
        //https://coil-kt.github.io/coil/recipes/
        binding.imageView.load(imageUrl){
            crossfade(true)
            crossfade(2000)
            size(100,100)
        }

        binding.imageView.setOnClickListener {
            val memoryCacheKey = binding.imageView.metadata?.memoryCacheKey
            if(memoryCacheKey!=null){
                binding.imageView.visibility = View.GONE
                binding.imageView2.visibility = View.VISIBLE
                binding.imageView2.load(imageUrl) {
                    crossfade(true)
                    crossfade(5000)
                    placeholderMemoryCacheKey(memoryCacheKey)
                }
            }
        }
        binding.imageView2.setOnClickListener {
            binding.imageView2.visibility = View.GONE
            binding.imageView.visibility = View.VISIBLE
        }

        binding.toGifSample.setOnClickListener {
            toGif()
        }
        binding.toSvgSample.setOnClickListener {
            toSvg()
        }
        binding.toVidFrameSample.setOnClickListener {
            toVid()
        }

        setContentView(view)

    }

    //get bitmap from imageURL
    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(applicationContext)
        val request = ImageRequest.Builder(applicationContext)
            .data(imageUrl)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun toGif(){
        val intent = Intent(this, GifActivity::class.java)
        startActivity(intent)
    }

    private fun toSvg(){
        val intent = Intent(this, SvgActivity::class.java)
        startActivity(intent)
    }

    private fun toVid(){
        val intent = Intent(this, VideoActivity::class.java)
        startActivity(intent)
    }



}
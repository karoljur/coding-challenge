package com.example.codingchallenge.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object GlideUtil
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, imagePath: String){
        Glide.with(view)
            .load(imagePath)
            .into(view)
    }
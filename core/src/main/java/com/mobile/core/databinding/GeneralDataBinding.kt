package com.mobile.core.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("url")
fun loadImage(view: ImageView, resourceUrl: String?) {
    resourceUrl ?: return
    Glide
        .with(view.context)
        .load(resourceUrl)
        .into(view)
}
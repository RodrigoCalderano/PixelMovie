package com.rodrigo.pixelmovie.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String?) {
    if (url == null || url.trim().isEmpty()) {
        setImageBitmap(null)
        return
    }

        Picasso.with(context).load(url).fit().into(this,
            object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                }

                override fun onError() {
                }
            })
    }

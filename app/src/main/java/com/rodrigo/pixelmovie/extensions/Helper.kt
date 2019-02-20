package com.rodrigo.pixelmovie.extensions

import android.support.annotation.IdRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

fun AppCompatActivity.setupToolbar(@IdRes id: Int, title: String? = null,
                                   upNavigation: Boolean = false): ActionBar {
    val toolbar = findViewById<Toolbar>(id)
    setSupportActionBar(toolbar)
    if(title != null){
        supportActionBar?.title = title
    }
    return supportActionBar!!
}


fun dateFormatter(releaseOld : String) : String{
    val aux = releaseOld.split("-".toRegex())
    return aux[2] + "/" + aux[1] + "/" +  aux[0]
}
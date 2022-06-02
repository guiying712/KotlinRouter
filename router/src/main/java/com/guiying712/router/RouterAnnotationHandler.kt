package com.guiying712.router

import android.content.Context

interface RouterAnnotationHandler {

    fun register(url: String, target: String)

    fun startUrl(context: Context, url :String)
}
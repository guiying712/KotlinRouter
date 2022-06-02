package com.guiying712.router

import android.content.Context
import android.content.Intent

class DefaultRouterAnnotationHandler  : RouterAnnotationHandler{

    private val map = HashMap<String,String>()

    override fun register(url: String, target: String) {
        map[url] = target
    }

    override fun startUrl(context: Context, url :String) {
        val className = map[url] ?: ""
        context.startActivity(intent(context, className))
    }

    private fun intent(context: Context, className: String): Intent {
        return  Intent().setClassName(context,className)
    }
}
package com.guiying712.router

import android.content.Context

object ARouter {

    private var handler :RouterAnnotationHandler = DefaultRouterAnnotationHandler()

    fun init() {


        Class.forName("com.guiying712.router.generated.LoaderInit")
            .getMethod("init")
            .invoke(null)
    }

    fun setAnnotationHandler(annotationHandler: RouterAnnotationHandler) {
        handler = annotationHandler
    }

    fun startUrl(context: Context, url :String) {
        handler.startUrl(context, url)
    }


}
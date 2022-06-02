package com.guiying712.demo

import com.guiying712.router.RouterAnnotationHandler
import com.guiying712.router.RouterAnnotationInit

class AnnotationInit_1dc0ff : RouterAnnotationInit {
    override fun init(handler: RouterAnnotationHandler) {
        handler.register("/main", "com.guiying712.demo.MainActivity")
        pring(1)
    }

    fun pring(a: Int) {
        print(a)
    }
}

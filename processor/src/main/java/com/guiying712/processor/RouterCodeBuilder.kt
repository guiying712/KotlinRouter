package com.guiying712.processor

import com.squareup.kotlinpoet.*
import java.io.File

/**
 * package com.router.generated
 *
 * class AnnotationInit_40eedb51f13f4801b6adae52d98e42df : RouterAnnotationInit {
 *      override fun init(handler: RouterAnnotationHandler) {
 *          handler.register("/demo", "com.guiying712.android.MainActivity")
 *          handler.register("/login", "com.guiying712.android.LoginActivity")
 *      }
 * }
 */
class RouterCodeBuilder(
    private val kaptKotlinGeneratedDir: String,
    private val fileName: String,
    private val code: CodeBlock
) {

    private val routerAnnotationInit = ClassName("com.guiying712.router", "RouterAnnotationInit")
    private val routerAnnotationHandler = ClassName("com.guiying712.router", "RouterAnnotationHandler")

    private val `package` = "com.guiying712.router"
    private val generatedPackage = "${`package`}.generated"


    fun buildFile() = FileSpec.builder(generatedPackage, fileName) // 2
        .addInitClass()
        .build()
        .writeTo(File(kaptKotlinGeneratedDir))

    private fun FileSpec.Builder.addInitClass() = apply {
        addType(
            TypeSpec.classBuilder(fileName)
                .addSuperinterface(routerAnnotationInit)
                .addInitMethod(code)
                .build()
        )
    }

    /**
     * 生成init方法的代码
     * handler.register("/demo", "com.guiying712.demo.MainActivity")
     */
    private fun TypeSpec.Builder.addInitMethod(code: CodeBlock) = apply {
        addFunction(
            FunSpec.builder("init")
                .addModifiers(KModifier.OVERRIDE)
                .addParameter("handler", routerAnnotationHandler)
                .returns(UNIT)
                .addCode(code)
                .build()
        )
    }


}
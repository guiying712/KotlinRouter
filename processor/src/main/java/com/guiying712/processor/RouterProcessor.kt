package com.guiying712.processor

import com.guiying712.annotations.Router
import com.squareup.kotlinpoet.*
import java.io.File
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RouterProcessor : AbstractProcessor() {

    private val logger by lazy { ProcessorLogger(processingEnv) }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun getSupportedAnnotationTypes() = mutableSetOf(Router::class.java.canonicalName) // 3

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        if (annotations == null || annotations.isEmpty()) return false

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false

        val codeBuilder = CodeBlock.Builder()
        val fileName = "AnnotationInit" + "_" + UUID.randomUUID().toString().replace("-", "")

        roundEnv.getElementsAnnotatedWith(Router::class.java) // 1
            .forEach { element -> // 2
                if (!validateActivity(element)) return false

                val annotation = element.getAnnotation(Router::class.java)
                val className = element.simpleName.toString()
                val url = annotation.url
                val packageName = processingEnv.elementUtils.getPackageOf(element).toString()

                val target = "$packageName.$className"
                codeBuilder.addStatement("handler.register(%S, %S)", url, target)
                logger.n("Router located: $target \n") // 5
            }
        RouterCodeBuilder(kaptKotlinGeneratedDir, fileName, codeBuilder.build()).buildFile()
        return true // 5
    }


    private fun validateActivity(element: Element): Boolean {
        (element as? TypeElement)?.let { // 1
            if (!processingEnv.typeUtils.isSubtype(element.asType(), processingEnv.elementUtils.getTypeElement("android.app.Activity").asType())) { // 2
                logger.e("Router注解只能标注Activity", element)
                return false
            }
            val modifiers = it.modifiers
            if (Modifier.ABSTRACT in modifiers) { // 3
                logger.e("Activity不可以是抽象类", element)
                return false
            }
            return true
        } ?: return false
    }


}


package com.guiying712.plugin

import com.android.SdkConstants
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import java.util.*
import java.io.File

class RouterTransform(private val project: Project) : Transform() {

    /**
     *  返回 transform 的名称，最终的task的名称会是：transformClassesWithRouterForDebug
     */
    override fun getName(): String = "Router"

    override fun getInputTypes(): Set<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean = false

    private fun TransformOutputProvider.getContentLocation(
        name: String,
        contentType: QualifiedContent.ContentType,
        scope: QualifiedContent.Scope,
        format: Format
    ): File = getContentLocation(name, setOf(contentType), EnumSet.of(scope), format)


    override fun transform(transformInvocation: TransformInvocation) {
        if (!transformInvocation.isIncremental) {
            transformInvocation.outputProvider.deleteAll()
        }

        val inputs = transformInvocation.inputs.flatMap { it.directoryInputs + it.jarInputs }
        val outputs: List<File> = inputs.map { input ->
            val format = if (input is JarInput) Format.JAR else Format.DIRECTORY
            // Transforms 可以要求获取给定范围、内容类型和格式的位置
            // 如果格式为 Format.DIRECTORY，则结果为目录的文件位置
            // 如果格式是 Format.JAR，那么结果是一个代表要创建的 jar 的文件
            transformInvocation.outputProvider.getContentLocation(
                input.name,
                input.contentTypes,
                input.scopes,
                format
            )
        }

        val dest  = transformInvocation.outputProvider.getContentLocation(
            "ARouter",
            QualifiedContent.DefaultContentType.CLASSES,
            QualifiedContent.Scope.PROJECT,
            Format.DIRECTORY
        )
        RouterProcessor(inputs.map { it.file }, outputs).apply {
            process(dest.absolutePath)
        }
    }

    fun process(inputs: List<File>, outputs: List<File>) {
        inputs.zip(outputs) { input, output ->
            when {
                input.isDirectory -> {
                    FileUtils.copyDirectory(input,output)
                }
                input.name.endsWith(SdkConstants.DOT_JAR) -> {
                    FileUtils.copyFile(input,output)
                }
            }
        }
    }

}

fun process(inputs: List<File>, outputs: List<File>) {
    inputs.zip(outputs) { input, output ->
        when {
            input.isDirectory -> {
                FileUtils.copyDirectory(input,output)
            }
            input.name.endsWith(SdkConstants.DOT_JAR) -> {
                FileUtils.copyFile(input,output)
            }
        }
    }
}
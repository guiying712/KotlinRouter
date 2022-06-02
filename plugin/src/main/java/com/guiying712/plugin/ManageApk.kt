package com.guiying712.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ManageApk : DefaultTask() {

    companion object {
        const val previousPath = "/outputs/apk/release"
        const val targetPath = "newOutput"

        const val previousName = "app-debug.apk"
        val newApkName = "${AppDetail.appName}-${getDate(false)}(${AppDetail.versionCode}).apk"
    }

    @TaskAction
    fun renameApk() {
        val newPath = File("${project.buildDir.absoluteFile}/$previousPath/$previousName")
        if (newPath.exists()) {
            val  newApkName = "${project.buildDir.absoluteFile}$previousPath$newApkName"
            newPath.renameTo(File(newApkName))
        } else {
            println("$newPath does not exist")
        }
        moveFile()
    }

    private fun moveFile() {
        File("${project.buildDir.absoluteFile}/$previousPath/$newApkName").let {sourceFile ->
            try {
                sourceFile.copyTo(File("$targetPath/$newApkName"))
            } catch (e: Exception) {
              e.printStackTrace()
                val folder = File(targetPath)
                folder.mkdir()
            } finally {
                sourceFile.delete()
            }
        }
    }
}
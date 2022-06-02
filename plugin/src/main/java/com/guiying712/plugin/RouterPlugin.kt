package com.guiying712.plugin

import com.android.build.gradle.internal.tasks.factory.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskProvider

class RouterPlugin : Plugin<Project> {

    lateinit var logger: Logger
    lateinit var project: Project

    override fun apply(project: Project) {
        this.project = project
        logger = Logging.getLogger(RouterPlugin::class.java)

        project.tasks.register("renameApk", ManageApk::class.java) {
            it.dependsOn("build")
        }
        project.android.registerTransform(RouterTransform(project))
        logger.info("结束了")
    }
}


package com.guiying712.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

val Project.androidType: BaseExtension
    get() = extensions.getByType(BaseExtension::class.java)

val Project.hasAndroid: Boolean
    get() = extensions.findByName("android") is BaseExtension

val Project.android: BaseExtension
    get() = extensions.getByName("android") as BaseExtension
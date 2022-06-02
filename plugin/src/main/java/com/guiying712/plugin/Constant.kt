package com.guiying712.plugin

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Dependencies {
  const val kotlinCore = "androidx.core:core-ktx:${Versions.kotlinCoreVersion}"
  const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
  const val material = "com.google.android.material:material:${Versions.materialVersion}"
  const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraintVersion}"
  const val jUnit = "junit:junit:${Versions.jUnitVersion}"
  const val jUnitExt = "androidx.test.ext:junit:${Versions.jUnitExtVersion}"
  const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"

  //
  const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinStdLibVersion}"
  const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinStdLibVersion}"
  const val buildToolGradle = "com.android.tools.build:gradle:${Versions.buildToolGradleVersion}"
}

object Versions {
  const val compileSdkVersion = 30
  const val buildToolsVersion = "30.0.3"
  const val minSdkVersion = 21
  const val targetSdkVersion = 30

  //
  const val kotlinStdLibVersion = "1.4.32"
  const val buildToolGradleVersion = "4.1.3"

  //
  const val kotlinCoreVersion = "1.3.2"
  const val appCompatVersion = "1.2.0"
  const val materialVersion = "1.3.0"
  const val constraintVersion = "2.0.4"
  const val jUnitVersion = "4.13.2"
  const val jUnitExtVersion = "1.1.2"
  const val espressoVersion = "3.3.0"
}


object AppDetail {
  const val applicationId = ""
  const val appName = "Router"
  const val versionCode = 1
  const val versionName = "1.0.0"

  const val previousPath = "/outputs/apk/debug"
  const val targetPath = "newOutput"

  //
  const val previousName = "app-debug.apk"
  val newApkName = "$appName-${getDate(false)}($versionCode).apk"

  //
  const val dependencyFileName = "Dependencies.txt"
}

fun getDate(forTxtFile: Boolean): String {
  val current = LocalDateTime.now()
  val formatter = if (forTxtFile)
    DateTimeFormatter.ofPattern("dd MMM, yyy")
  else
    DateTimeFormatter.ofPattern("yyyyMMdd")
  return current.format(formatter)
}
package com.guiying712.plugin

import com.android.SdkConstants
import com.guiying712.plugin.ClassGenerator
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOCase
import org.apache.commons.io.filefilter.SuffixFileFilter
import org.apache.commons.io.filefilter.TrueFileFilter
import java.io.File
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.jar.JarFile

class RouterProcessor(
    private val inputs: List<File>,
    private val outputs: List<File>
) {

    private val `package` = "com.guiying712.router"
    private val generatedPackage = "${`package`}.generated"

    /**
     * Linux/Unix： com/guiying712/router/generated
     * Windows： com\guiying712\router\generated
     */
    private val initDirectory = generatedPackage.replace(".", File.separator)

    /**
     * com/guiying712/router/generated
     */
    private val initPath = generatedPackage.replace(".", "/")

    private val initClasses = Collections.newSetFromMap(ConcurrentHashMap<String, Boolean>())

    fun process(direction: String) {
        inputs.zip(outputs) { input, output ->
            when {
                input.isDirectory -> {
                    processDirectory(input, initClasses)
                    FileUtils.copyDirectory(input,output)
                }
                input.name.endsWith(SdkConstants.DOT_JAR) -> {
                    processJar(input, initClasses)
                    FileUtils.copyFile(input,output)
                }
            }
        }


        ClassGenerator(direction ,initClasses).generate()
    }

    private fun processJar(file: File, initClasses: MutableSet<String>) {
        JarFile(file).entries().asSequence().forEach {
            if (it.name.endsWith(SdkConstants.DOT_CLASS) && it.name.startsWith(initPath)) {
                val className = it.name.replace("/", ".")
                initClasses.add(className)
                println(className)
            }
        }
    }

    private fun processDirectory(file: File, initClasses: MutableSet<String>) {
        with(File(file, initDirectory)) {
            if (exists() && isDirectory) {
                FileUtils.listFiles(
                    this,
                    SuffixFileFilter(SdkConstants.DOT_CLASS, IOCase.INSENSITIVE),
                    TrueFileFilter.INSTANCE
                ).forEach {
                    val className = qualifiedName(it.absolutePath, file.absolutePath.length + 1)
                        .replace(File.separator, ".")
                    initClasses.add(className)
                    println(className)
                }
            }
        }
    }

    /**
     * [prefix]com/xxx/aaa.class --> com/xxx/aaa
     * [prefix]com\xxx\aaa.class --> com\xxx\aaa
     */
    private fun qualifiedName(s: String, start: Int): String {
        return s.substring(start, s.length - SdkConstants.DOT_CLASS.length)
    }
}
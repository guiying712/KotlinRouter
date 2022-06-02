package com.guiying712.plugin

import com.android.SdkConstants
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File
import java.io.FileOutputStream


class ClassGenerator(
    private val direction: String,
    private val classes: Set<String>
) {

    fun generate() {
        if (classes.isEmpty()) return

        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES or ClassWriter.COMPUTE_MAXS)
        // 必须按照指定顺序调用
        val classVisitor = object : ClassVisitor(Opcodes.ASM5, classWriter) {}

        val className = "com.guiying712.router.generated.LoaderInit".replace('.', '/')
        // 访问类的头文件, 50代表JDK 1.7
        classVisitor.visit(50, Opcodes.ACC_PUBLIC,className,null, "java/lang/Object", null)

        // 访问类的方法
       val methodVisitor = classVisitor.visitMethod(Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC, "init","()V",null,null)

        methodVisitor.apply {
            visitCode() // 访问该方法的代码
            classes.forEach { clazz ->
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, clazz.replace(".","/"),"init","()V",false)
            }
            visitMaxs(0,0)
            visitInsn(Opcodes.RETURN)
            visitEnd() //访问方法结束
        }
        classVisitor.visitEnd()

        File(direction,className+SdkConstants.DOT_CLASS).apply {
            parentFile.mkdirs()
            FileOutputStream(this).write(classWriter.toByteArray())
        }
    }
}
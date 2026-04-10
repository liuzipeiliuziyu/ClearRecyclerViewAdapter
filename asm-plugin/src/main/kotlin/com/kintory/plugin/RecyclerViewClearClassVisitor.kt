package com.kintory.plugin

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class RecyclerViewClearClassVisitor(
    nextClassVisitor: ClassVisitor,
    private val fragmentBase: String
) : ClassVisitor(Opcodes.ASM9, nextClassVisitor) {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (name == "onDestroyView" && descriptor == "()V") {
            // 传递确定的基类路径
            return RecyclerViewClearMethodVisitor(mv, fragmentBase)
        }
        return mv
    }
}
package com.kintory.plugin

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class RecyclerViewClearMethodVisitor(mv: MethodVisitor, private val fragmentBase: String) : MethodVisitor(
    Opcodes.ASM9, mv) {

    override fun visitCode() {
        super.visitCode()

        // 注入逻辑：RecyclerViewCleaner.clear(this.getView())
        mv.visitVarInsn(Opcodes.ALOAD, 0) // 加载 this
        mv.visitMethodInsn(
            Opcodes.INVOKEVIRTUAL,
            fragmentBase, // 动态使用 androidx 或 android/app 路径
            "getView",
            "()Landroid/view/View;",
            false
        )
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "com/kintory/clear_recycler_view_adapter/RecyclerViewCleaner",
            "clear",
            "(Landroid/view/View;)V",
            false
        )
    }
}
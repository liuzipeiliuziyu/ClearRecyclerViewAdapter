package com.kintory.plugin

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import org.objectweb.asm.ClassVisitor

abstract class RecyclerViewClearFactory : AsmClassVisitorFactory<RecyclerViewClearParameters> {
    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        val superClasses = classContext.currentClassData.superClasses
        val fragmentBase = when {
            superClasses.contains("androidx.fragment.app.Fragment") -> "androidx/fragment/app/Fragment"
            superClasses.contains("android.app.Fragment") -> "android/app/Fragment"
            else -> "androidx/fragment/app/Fragment"
        }
        return RecyclerViewClearClassVisitor(nextClassVisitor, fragmentBase)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        val filters = parameters.get().packageFilters.getOrElse(emptyList())
        if (filters.isNotEmpty()) {
            val isMatch = filters.any { classData.className.startsWith(it) }
            if (!isMatch) return false
        }

        // 这里的 superClasses 包含所有上级父类，确保自定义 BaseFragment 的子类也能被匹配上
        return classData.superClasses.contains("androidx.fragment.app.Fragment") ||
               classData.superClasses.contains("android.app.Fragment") ||
                classData.className.endsWith("Fragment")
    }
}
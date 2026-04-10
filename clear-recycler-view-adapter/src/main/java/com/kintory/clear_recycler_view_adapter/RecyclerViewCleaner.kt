package com.kintory.clear_recycler_view_adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewCleaner {
    /**
     * 遍历 View 树，清除所有 RecyclerView 的 Adapter 以防止内存泄漏。
     * 使用 @JvmStatic 确保 ASM 插件可以通过 INVOKESTATIC 正常调用。
     */
    @JvmStatic
    fun clear(view: View?) {
        when (view) {
            null -> return
            is RecyclerView -> {
                view.adapter = null
                return
            }
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    clear(view.getChildAt(i))
                }
            }
        }
    }
}

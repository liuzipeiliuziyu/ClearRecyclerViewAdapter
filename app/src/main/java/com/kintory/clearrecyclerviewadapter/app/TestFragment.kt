package com.kintory.clearrecyclerviewadapter.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = RecyclerView(requireContext())
        recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = TODO()
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit
            override fun getItemCount(): Int = 0
        }
        return recyclerView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 插件会自动在这里插入 RecyclerViewCleaner.clear(getView())
    }
}

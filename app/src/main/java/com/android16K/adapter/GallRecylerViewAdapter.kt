package com.android16K.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android16K.databinding.RecycleGallBinding
import com.android16K.dataset.TestData
import com.android16K.dataset.gall.Gallery

class GallRecylerViewAdapter: RecyclerView.Adapter<GallRecylerViewAdapter.MyViewHolder>() {
    var testList = mutableListOf<TestData>()

    inner class MyViewHolder(
        private val binding: RecycleGallBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(gallData: TestData){
            binding.testTitle.text = gallData.title
            binding.testContent.text = gallData.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecycleGallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(testList[position])
    }

    override fun getItemCount(): Int = testList.size
}
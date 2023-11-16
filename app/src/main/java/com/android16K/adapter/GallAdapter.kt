package com.android16K.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.android16K.R
import com.android16K.dataset.gall.Gallery

class GallAdapter() : PagingDataAdapter<Gallery, GallViewHolder>(Gallery.DiffCallBack) {
    override fun onBindViewHolder(holder: GallViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null)
            holder.textView.text = item.content
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GallViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_gall, parent, false)
        return GallViewHolder(view)
    }
}
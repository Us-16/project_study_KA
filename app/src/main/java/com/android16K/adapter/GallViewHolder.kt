package com.android16K.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android16K.R

class GallViewHolder(
    itemView: View
): RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.test_text)
}
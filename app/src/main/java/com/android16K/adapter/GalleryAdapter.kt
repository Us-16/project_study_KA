package com.android16K.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.android16K.R
import com.android16K.dataset.Gallery

class GalleryAdapter(private val context: Context, private val gallList: List<Gallery>?): BaseAdapter() {
    override fun getCount(): Int {
        return gallList!!.size
    }

    override fun getItem(position : Int): Any {
        return gallList!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_gall, null)

        val gallTitle = view.findViewById<TextView>(R.id.gall_form_title)
        val gallContent = view.findViewById<TextView>(R.id.gall_content)

        val gall = gallList!![position]

        gallTitle.text = gall.title
        gallContent.text = gall.content

        return view
    }
}
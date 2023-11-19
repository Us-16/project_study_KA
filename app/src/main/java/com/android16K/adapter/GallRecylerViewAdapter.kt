package com.android16K.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android16K.activity.GalleryDetailActivity
import com.android16K.databinding.RecycleGallBinding
import com.android16K.dataset.TestData
import com.android16K.dataset.gall.Gallery

class GallRecylerViewAdapter: PagingDataAdapter<Gallery, GallRecylerViewAdapter.MyViewHolder>(
    diffCallback
) {
    inner class MyViewHolder(
        private val binding: RecycleGallBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(gallData: Gallery){
            binding.testTitle.text = gallData.title
            binding.testContent.text = gallData.content

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, GalleryDetailActivity::class.java)
                intent.putExtra("gall_id", gallData.id)
                //intent.putExtra("galleryId", gallData.id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecycleGallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    //override fun getItemCount(): Int = testList.size

    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<Gallery>(){
            override fun areItemsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Gallery, newItem: Gallery): Boolean {
                return oldItem == newItem
            }
        }
    }
}
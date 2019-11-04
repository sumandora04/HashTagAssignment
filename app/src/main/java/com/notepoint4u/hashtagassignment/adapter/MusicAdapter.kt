package com.notepoint4u.hashtagassignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.notepoint4u.hashtagassignment.databinding.SingleMusicCellBinding
import com.notepoint4u.hashtagassignment.network.MusicDetail

class MusicAdapter:ListAdapter<MusicDetail, MusicAdapter.MusicHolder>(MusicDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        return MusicHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class MusicHolder private constructor(val binding:SingleMusicCellBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:MusicDetail) {
            binding.musicDetail = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MusicHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleMusicCellBinding.inflate(layoutInflater, parent, false)
                return MusicHolder(binding)
            }
        }
    }

    class MusicDiffCallBack:DiffUtil.ItemCallback<MusicDetail>(){
        override fun areItemsTheSame(oldItem: MusicDetail, newItem: MusicDetail): Boolean {
            return oldItem.musicTackName.equals(newItem.musicTackName)
        }

        override fun areContentsTheSame(oldItem: MusicDetail, newItem: MusicDetail): Boolean {
            return oldItem == newItem
        }
    }

}
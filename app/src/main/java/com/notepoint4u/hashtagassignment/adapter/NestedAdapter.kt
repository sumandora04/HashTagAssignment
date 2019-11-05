package com.notepoint4u.hashtagassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notepoint4u.hashtagassignment.R
import kotlinx.android.synthetic.main.single_music_cell.view.*

class NestedAdapter(val musicList: List<String>) : RecyclerView.Adapter<NestedAdapter.MusicTrackHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicTrackHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_music_cell, parent, false)
        return MusicTrackHolder(v)
    }

    override fun onBindViewHolder(holder: MusicTrackHolder, position: Int) {
        val item = musicList.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    class MusicTrackHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(item:String) {
            itemView.music_name_single_cell_text_view.text = item
        }
    }
}
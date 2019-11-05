package com.notepoint4u.hashtagassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout.HORIZONTAL
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.notepoint4u.hashtagassignment.R
import com.notepoint4u.hashtagassignment.activity.ARTIST_FLAG
import com.notepoint4u.hashtagassignment.viewModels.MusicViewModel
import kotlinx.android.synthetic.main.nested_recycler_view.view.*

class MusicAdapter(
    private val artistOrAlbumList: List<String>,
    val flag: Int,
    val viewModel: MusicViewModel,
    val lifecycleOwner: LifecycleOwner,
    val context: Context,
    val columns:Int
) : RecyclerView.Adapter<MusicAdapter.MusicHolder>() {

    override fun getItemCount(): Int {
        return artistOrAlbumList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.nested_recycler_view, parent, false)
        return MusicHolder(v, flag,viewModel, lifecycleOwner,context,columns)
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        val item = artistOrAlbumList.get(position)
        holder.bind(item)
    }

    class MusicHolder(itemView: View,val flag: Int, val viewModel: MusicViewModel, val lifecycleOwner: LifecycleOwner, val context: Context,val columns:Int) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            itemView.header_text_nested_recycler.text = item

            if (flag== ARTIST_FLAG) {
                viewModel.getMusicTrackForArtist(item)?.observe(lifecycleOwner, Observer {
                    it.let {
                        val adapter = NestedAdapter(it)
                        itemView.nested_music_recycler.layoutManager =
                            GridLayoutManager(context, columns, HORIZONTAL, false) as RecyclerView.LayoutManager?
                        itemView.nested_music_recycler.adapter = adapter
                    }
                })
            }else{
                viewModel.getMusicTrackForAlbum(item)?.observe(lifecycleOwner, Observer {
                    it.let {
                        val adapter = NestedAdapter(it)
                        itemView.nested_music_recycler.layoutManager =
                            GridLayoutManager(context, columns, HORIZONTAL, false) as RecyclerView.LayoutManager?
                        itemView.nested_music_recycler.adapter = adapter
                    }
                })
            }

        }
    }

}
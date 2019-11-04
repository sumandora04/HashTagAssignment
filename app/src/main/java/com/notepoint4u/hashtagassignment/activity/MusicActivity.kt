package com.notepoint4u.hashtagassignment.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.notepoint4u.hashtagassignment.adapter.MusicAdapter
import com.notepoint4u.hashtagassignment.viewModels.MusicViewModel
import com.notepoint4u.hashtagassignment.R
import com.notepoint4u.hashtagassignment.database.MusicDatabase
import com.notepoint4u.hashtagassignment.databinding.ActivityMainBinding
import com.notepoint4u.hashtagassignment.viewModels.MusicViewModelFactory

class MusicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MusicViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val application = requireNotNull(application)
        val dataSource = MusicDatabase.getDatabaseInstance(application).musicDao
        val viewModelFactory = MusicViewModelFactory(dataSource,application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MusicViewModel::class.java)
        binding.musicViewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MusicAdapter()
        binding.musicRecyclerView.layoutManager = GridLayoutManager(this,2) as RecyclerView.LayoutManager?
        binding.musicRecyclerView.adapter = adapter


        viewModel.getMusicListFromDb()?.observe(this, Observer {
            it.let {
                adapter.submitList(it)
            }
        })

    }


}

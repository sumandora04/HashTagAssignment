package com.notepoint4u.hashtagassignment.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.notepoint4u.hashtagassignment.R
import com.notepoint4u.hashtagassignment.adapter.MusicAdapter
import com.notepoint4u.hashtagassignment.database.MusicDatabase
import com.notepoint4u.hashtagassignment.databinding.ActivityMainBinding
import com.notepoint4u.hashtagassignment.viewModels.MusicViewModel
import com.notepoint4u.hashtagassignment.viewModels.MusicViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filter_dialog.*

const val ARTIST_FLAG = 0
const val ALBUM_FLAG = 1

class MusicActivity : AppCompatActivity() {

    val columns = arrayOf(2, 3, 4, 5)
    var row = 2;
    var currentArtistAlbumFilter = ARTIST_FLAG

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MusicViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val application = requireNotNull(application)
        val dataSource = MusicDatabase.getDatabaseInstance(application).musicDao
        val viewModelFactory = MusicViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MusicViewModel::class.java)
        binding.musicViewModel = viewModel
        binding.lifecycleOwner = this


        artist_album_filter.text = "Artist"
        viewModel.getArtistFromDb()?.observe(this, Observer {
            it.let {
                val adapter = MusicAdapter(it, ARTIST_FLAG, viewModel, this, this, row)
                binding.musicRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.musicRecyclerView.adapter = adapter
            }
        })

        artist_album_filter.setOnClickListener {
            filterDialog()
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            columns
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        rows_filter_spinner.adapter = adapter

        rows_filter_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                row = parent.getItemAtPosition(position) as Int

                if (currentArtistAlbumFilter == ARTIST_FLAG) {
                    viewModel.getArtistFromDb()?.observe(this@MusicActivity, Observer {
                        it.let {
                            val musicAdapter =
                                MusicAdapter(it, ARTIST_FLAG, viewModel, this@MusicActivity, this@MusicActivity, row)
                            binding.musicRecyclerView.layoutManager = LinearLayoutManager(this@MusicActivity)
                            binding.musicRecyclerView.adapter = musicAdapter
                        }
                    })
                } else {
                    viewModel.getAlbumFromDb()?.observe(this@MusicActivity, Observer {
                        it.let {
                            val musicAdapter =
                                MusicAdapter(it, ALBUM_FLAG, viewModel, this@MusicActivity, this@MusicActivity, row)
                            binding.musicRecyclerView.layoutManager = LinearLayoutManager(this@MusicActivity)
                            binding.musicRecyclerView.adapter = musicAdapter
                        }
                    })

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }


    }


    fun filterDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.filter_dialog)


        dialog.artist_filter.setOnClickListener(View.OnClickListener {
            artist_album_filter.text = "Artist"
            viewModel.getArtistFromDb()?.observe(this, Observer {
                it.let {
                    val adapter = MusicAdapter(it, ARTIST_FLAG, viewModel, this, this, row)
                    binding.musicRecyclerView.layoutManager = LinearLayoutManager(this)
                    binding.musicRecyclerView.adapter = adapter
                }
            })
            dialog.dismiss()

        })

        dialog.album_filter.setOnClickListener(View.OnClickListener {
            artist_album_filter.text = "Album"
            viewModel.getAlbumFromDb()?.observe(this, Observer {
                it.let {
                    val adapter = MusicAdapter(it, ALBUM_FLAG, viewModel, this, this, row)
                    binding.musicRecyclerView.layoutManager = LinearLayoutManager(this)
                    binding.musicRecyclerView.adapter = adapter
                }
            })
            dialog.dismiss()
        })

        dialog.show()

    }

}

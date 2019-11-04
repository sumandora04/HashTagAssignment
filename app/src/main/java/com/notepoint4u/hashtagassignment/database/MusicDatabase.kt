package com.notepoint4u.hashtagassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.notepoint4u.hashtagassignment.network.MusicDetail

@Database(entities = [MusicDetail::class], version = 1, exportSchema = false)
abstract class MusicDatabase : RoomDatabase() {
    abstract val musicDao: MusicDao

    companion object {
        @Volatile
        private var INSTANCE: MusicDatabase? = null

        fun getDatabaseInstance(context: Context): MusicDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MusicDatabase::class.java,
                        "music_databse"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
package com.smenedi.modernandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseUser::class, DatabaseRepo::class], version = 1, exportSchema = false)
abstract class GitDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: GitDatabase

        fun getInstance(context: Context): GitDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, GitDatabase::class.java, "git_database").build()
                }
                return INSTANCE

            }
        }
    }
}

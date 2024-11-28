package com.example.repofinder.data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.repofinder.data.Model.Repository

@Database(entities = [Repository::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao

    companion object {
        const val DATABASE_NAME = "github_search_db"
    }
}
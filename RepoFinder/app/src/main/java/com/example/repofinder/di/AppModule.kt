package com.example.repofinder.di

import android.content.Context
import androidx.room.Room
import com.example.repofinder.data.Local.AppDatabase
import com.example.repofinder.data.Remote.RetrofitInstance
import com.example.repofinder.data.Remote.RetrofitInstance.apiService
import com.example.repofinder.data.Repository.GithubRepository

object RepositoryProvider {

    // Singleton Room database instance
    @Volatile
    private var INSTANCE: AppDatabase? = null

    // Thread-safe way to initialize the database
    private fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).build()
            INSTANCE = instance
            instance
        }
    }

    fun provideGithubRepository(context: Context): GithubRepository {
        val database = getDatabase(context) // Use the singleton database instance
        val apiService = RetrofitInstance.apiService // Assuming RetrofitInstance is correctly implemented

        return GithubRepository(apiService, database.repositoryDao()) // Pass DAO to GithubRepository
    }
}

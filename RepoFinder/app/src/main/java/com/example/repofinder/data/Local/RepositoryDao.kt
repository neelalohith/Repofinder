package com.example.repofinder.data.Local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.repofinder.data.Model.Repository

@Dao
interface RepositoryDao {

    // Insert a list of repositories into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<Repository>)

    // Get the first 15 saved repositories
    @Query("SELECT * FROM repositories LIMIT 15")
    suspend fun getSavedRepositories(): List<Repository>

    // Clear all repositories (optional)
    @Query("DELETE FROM repositories")
    suspend fun clearAllRepositories()
}
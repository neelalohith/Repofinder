package com.example.repofinder.data.Repository

import com.example.repofinder.data.Local.RepositoryDao
import com.example.repofinder.data.Model.Repository as RoomRepository // Local database model
import com.example.repofinder.data.Remote.ApiService
import com.example.repofinder.data.Remote.Repository as ApiRepository // Remote API model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepository(
    private val apiService: ApiService,
    private val repositoryDao: RepositoryDao
) {

    // Search repositories from the API and cache them in the database
    suspend fun searchRepositories(query: String, page: Int, perPage: Int): List<RoomRepository> {
        return withContext(Dispatchers.IO) {
            try {
                // Fetch repositories from the API
                val response = apiService.searchRepositories(query, page, perPage)

                // Map API response to RoomRepository objects
                val roomRepositories = response.items.map { item ->
                    RoomRepository(
                        id = item.id,
                        name = item.name,
                        owner = item.owner, // Corrected the owner field (it should be 'login')
                        description = item.description,
                        html_url = item.html_url
                    )
                }

                // If it's the first page, clear existing repositories in the database
                if (page == 1) {
                    repositoryDao.clearAllRepositories()
                }

                // Insert repositories into the database
                repositoryDao.insertRepositories(roomRepositories)

                // Return the list of RoomRepositories
                roomRepositories
            } catch (e: Exception) {
                // Handle error (log it, rethrow it, etc.)
                emptyList() // Return an empty list in case of an error
            }
        }
    }

    // Get cached repositories from the local database
    suspend fun getCachedRepositories(): List<RoomRepository> {
        return withContext(Dispatchers.IO) {
            try {
                repositoryDao.getSavedRepositories()
            } catch (e: Exception) {
                // Handle error (log it, rethrow it, etc.)
                emptyList() // Return an empty list in case of an error
            }
        }
    }

    // Fetch contributors for a specific repository
    suspend fun getContributors(
        owner: String,
        repo: String
    ): List<com.example.repofinder.data.Remote.Contributor> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getContributors(owner, repo)
            } catch (e: Exception) {
                // Handle error (log it, rethrow it, etc.)
                emptyList() // Return an empty list in case of an error
            }
        }
    }

    // Fetch repositories for a specific contributor
    suspend fun getContributorRepos(username: String): List<ApiRepository> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getContributorRepos(username)
            } catch (e: Exception) {
                // Handle error (log it, rethrow it, etc.)
                emptyList() // Return an empty list in case of an error
            }
        }
    }

    // Save repositories to Room (manual insertion)
    suspend fun saveRepositories(repositories: List<RoomRepository>) {
        withContext(Dispatchers.IO) {
            try {
                repositoryDao.insertRepositories(repositories)
            } catch (e: Exception) {
                // Handle error (log it, rethrow it, etc.)
            }
        }
    }

    // Fetch repository details and save them to the database
    suspend fun getRepositoryDetails(owner: String, repo: String): ApiRepository? {
        return withContext(Dispatchers.IO) {
            try {
                // Fetch repository details from the API
                val apiRepository = apiService.getRepositoryDetails(owner, repo)

                // Map inline and save to database
                val roomRepository = RoomRepository(
                    id = apiRepository.id,
                    name = apiRepository.name,
                    owner = apiRepository.owner, // Corrected the owner field (it should be 'login')
                    description = apiRepository.description,
                    html_url = apiRepository.html_url // Use camelCase for consistency
                )

                // Insert the repository into the database
                repositoryDao.insertRepositories(listOf(roomRepository))

                // Return the API repository
                apiRepository
            } catch (e: Exception) {
                // Handle error (log it, rethrow it, etc.)
                null // Return null in case of an error
            }
        }
    }
}

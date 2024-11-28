package com.example.repofinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repofinder.data.Model.Repository
import com.example.repofinder.data.Repository.GithubRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Define UI state to represent the loading, error, and data states
data class HomeUiState(
    val repositories: List<Repository> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "", // Store the current query
    val hasMore: Boolean = true // Flag to indicate if more repositories are available
)

class HomeViewModel(private val githubRepository: GithubRepository) : ViewModel() {

    private var currentPage = 1  // Track the current page for pagination
    private val perPage = 10     // Number of items to fetch per page
    private val _uiState = MutableStateFlow(HomeUiState()) // Mutable state for UI updates
    val uiState: StateFlow<HomeUiState> = _uiState // Expose the state to the UI

    // Function to search for repositories
    fun searchRepositories(query: String) {
        if (query.isBlank()) return // Return early if query is empty

        // If the query changes, reset currentPage to 1 for fresh pagination
        if (query != _uiState.value.query) {
            currentPage = 1
        }

        // Update UI to show loading state before fetching
        _uiState.value = HomeUiState(isLoading = true, query = query)

        viewModelScope.launch {
            try {
                // Fetch repositories from the API using the current page, query, and perPage
                val repositories = githubRepository.searchRepositories(query, currentPage, perPage)

                // Cache repositories for the first page
                if (currentPage == 1) {
                    githubRepository.saveRepositories(repositories)
                }

                // Determine if more repositories exist based on the number of fetched items
                val hasMore = repositories.size == perPage

                // Update the UI state with the fetched repositories, stop loading, and indicate if more data exists
                _uiState.value = HomeUiState(
                    repositories = _uiState.value.repositories + repositories, // Append the new repositories to the list
                    isLoading = false,
                    query = query,
                    hasMore = hasMore
                )

                // Increment current page for pagination
                if (hasMore) currentPage++

            } catch (e: Exception) {
                // Handle any errors and update UI with error information
                _uiState.value = HomeUiState(isLoading = false, error = e.message, query = query)
            }
        }
    }

    // Function to reset the currentPage if needed (e.g., on user logout or search reset)
    fun resetPagination() {
        currentPage = 1
    }
}

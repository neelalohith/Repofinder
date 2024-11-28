package com.example.repofinder.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repofinder.data.Model.Contributor
import com.example.repofinder.data.Model.Repository
import com.example.repofinder.data.Repository.GithubRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RepoDetailsUiState(
    val repository: com.example.repofinder.data.Remote.Repository? = null, // Repository data, null initially
    val isLoading: Boolean = false,     // Loading state
    val error: String? = null,          // Error message if any
    val contributors: List<Contributor> = emptyList() // List of contributors, empty initially
)

class RepoDetailsViewModel(private val githubRepository: GithubRepository) : ViewModel() {

    private val _repositories = MutableStateFlow<List<Repository>>(emptyList()) // StateFlow for repositories
    val repositories: StateFlow<List<Repository>> = _repositories // Exposing as StateFlow

    // StateFlow to store contributors
    private val _contributors = MutableStateFlow<List<Contributor>>(emptyList())
    val contributors: StateFlow<List<Contributor>> get() = _contributors

    // UI State for the repository details page
    private val _uiState = MutableStateFlow(RepoDetailsUiState()) // Mutable state for UI updates
    val uiState: StateFlow<RepoDetailsUiState> = _uiState // Expose the state to the UI

    // Function to fetch repository details
    fun fetchRepoDetails(owner: String, repo: String) {
        // Set loading state before fetching
        _uiState.value = RepoDetailsUiState(isLoading = true)

        viewModelScope.launch {
            try {
                // Fetch repository details from the API
                val repository = githubRepository.getRepositoryDetails(owner, repo)

                // Update the UI state with the fetched repository and stop loading
                _uiState.value = RepoDetailsUiState(repository = repository, isLoading = false)
            } catch (e: Exception) {
                // Handle any errors and update the UI with error information
                _uiState.value = RepoDetailsUiState(isLoading = false, error = e.message)
            }
        }
    }

    fun fetchAndSaveRepositories(query: String) {
        viewModelScope.launch {
            try {
                _uiState.value = RepoDetailsUiState(isLoading = true)

                // Fetch repositories from the API using the query
                val response = githubRepository.searchRepositories(query, 1, 30) // Fetching 30 repositories

                // Map the API response to RoomRepository objects
                val roomRepositories = response.map {
                    Repository(
                        id = it.id,
                        name = it.name,
                        owner = it.owner, // Assuming you want the owner's login as a string
                        description = it.description,
                        html_url = it.html_url
                    )
                }

                // Update the repositories list in the UI state
                _repositories.value = roomRepositories

                // Save repositories to Room database
                githubRepository.saveRepositories(roomRepositories)

                // Set UI state to loaded (no longer loading)
                _uiState.value = RepoDetailsUiState(isLoading = false)

            } catch (e: Exception) {
                // Handle the error and update the UI state
                _uiState.value = RepoDetailsUiState(isLoading = false, error = e.message)
            }
        }
    }

    // Function to fetch contributors for a repository
    fun fetchContributors(owner: String, repo: String) {
        _uiState.value = RepoDetailsUiState(isLoading = true)

        viewModelScope.launch {
            try {
                // Fetch contributors from the GitHub repository
                val contributorsList = githubRepository.getContributors(owner, repo)

                // Ensure contributorsList is not null and properly mapped to the correct type
                if (contributorsList.isNotEmpty()) {
                    // Safely set contributorsList to _contributors state
                    _contributors.value = contributorsList
                } else {
                    // If contributorsList is empty, set to an empty list or handle as needed
                    _contributors.value = emptyList() // Optional: show a message for no contributors
                }

                // Update the loading state to false once the data is fetched
                _uiState.value = RepoDetailsUiState(isLoading = false)

            } catch (e: Exception) {
                // Handle error and set the error message in UI state
                _uiState.value = RepoDetailsUiState(isLoading = false, error = e.message)
            }
        }
    }

    // Function to handle opening the repository link (optional)
    fun openLink(url: String, context: Context) {
        // Create an Intent to open the link in a browser
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}


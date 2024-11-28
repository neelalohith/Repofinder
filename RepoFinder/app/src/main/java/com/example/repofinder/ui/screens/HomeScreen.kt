package com.example.repofinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.repofinder.data.Model.Repository
import com.example.repofinder.ui.components.RepoItem
import com.example.repofinder.ui.components.SearchBar
import com.example.repofinder.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Search Bar
        SearchBar(
            placeholderText = "Search GitHub repositories...",
            onSearch = { query -> viewModel.searchRepositories(query) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Repository List
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.repositories) { repo ->
                RepoItem(
                    repository = repo,
                    onClick = { onRepoClick(repo, navController) }
                )
                Divider()
            }

            // Pagination Loader
            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

// Function to handle repository click
private fun onRepoClick(repository: Repository, navController: NavController) {
    navController.navigate("repoDetails/${repository.id}")
}
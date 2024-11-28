package com.example.repofinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.repofinder.viewmodel.RepoDetailsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Use Material3 default theme
            MaterialTheme {
                // Display the RepoDetailsScreen
                RepoDetailsScreen()
            }
        }
    }
}

@Composable
fun RepoDetailsScreen(viewModel: RepoDetailsViewModel = viewModel()) {
    // Collect the repositories and contributors from the ViewModel
    val repositories = viewModel.repositories.collectAsState(initial = emptyList()).value
    val contributors = viewModel.contributors.collectAsState(initial = emptyList()).value
    val uiState = viewModel.uiState.collectAsState().value

    // Fetch repositories when the screen is launched
    LaunchedEffect(Unit) {
        viewModel.fetchAndSaveRepositories("android")  // Example query for GitHub repositories
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Show repositories list
        Text("Repositories", style = MaterialTheme.typography.titleLarge)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(repositories) { repo ->
                RepoItem(repository = repo, onClick = {
                    // Fetch contributors when a repository is clicked
                    viewModel.fetchContributors(repo.owner, repo.name)
                })
            }
        }

        // Show contributors list below repositories
        Spacer(modifier = Modifier.height(16.dp))
        Text("Contributors", style = MaterialTheme.typography.titleLarge)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(contributors) { contributor ->
                ContributorItem(contributor = contributor)
            }
        }

        // Show loading spinner while data is being fetched
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        // Show error message if any
        uiState.error?.let {
            Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun RepoItem(repository: com.example.repofinder.data.Remote.Repository, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(repository.name, style = MaterialTheme.typography.titleMedium)
            Text(repository.owner, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun ContributorItem(contributor: com.example.repofinder.data.Model.Contributor) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(contributor.login, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        RepoDetailsScreen()
    }
}

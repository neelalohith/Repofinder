package com.example.repofinder.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.repofinder.data.Model.Repository
import com.example.repofinder.viewmodel.RepoDetailsViewModel

@Composable
fun RepoDetailsScreen(
    repository: Repository,
    onContributorClick: (String) -> Unit,
    viewModel: RepoDetailsViewModel = viewModel()
) {
    val context = LocalContext.current // Get the current context
    val contributors by viewModel.contributors.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchContributors(repository.owner, repository.name)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Repository Details
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(repository.html_url),
                contentDescription = null,
                modifier = Modifier.size(64.dp).padding(end = 16.dp)
            )
            Column {
                Text(repository.name, style = MaterialTheme.typography.titleLarge)
                Text(repository.owner, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Description:", style = MaterialTheme.typography.titleMedium)
        Text(repository.description ?: "No description available", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Project Link:", style = MaterialTheme.typography.titleMedium)
        Text(
            repository.html_url,
            modifier = Modifier.clickable { viewModel.openLink(repository.html_url, context) }, // Pass context here
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Contributors:", style = MaterialTheme.typography.titleMedium)

        // Contributors List
        LazyColumn {
            items(contributors) { contributor ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onContributorClick(contributor.login) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(contributor.avatar_url),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).padding(end = 8.dp)
                    )
                    Text(contributor.login, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}

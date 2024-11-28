package com.example.repofinder.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.repofinder.data.Model.Repository  // Ensure correct import for Repository
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow

@Composable
fun RepoItem(repository: Repository, onClick: () -> Unit) {
    // Wrapper Box to add custom shadow
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding around the Card
            .clickable { onClick() }
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.medium) // Custom shadow for elevation effect
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Padding inside the card
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = Color.White // Default background for the card
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Display repository name using MaterialTheme.typography
                Text(repository.name, style = MaterialTheme.typography.titleLarge)

                // Display the repository owner's login
                Text(repository.owner, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


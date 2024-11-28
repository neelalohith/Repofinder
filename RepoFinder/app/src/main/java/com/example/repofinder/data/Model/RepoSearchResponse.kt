package com.example.repofinder.data.Model

data class RepoSearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)
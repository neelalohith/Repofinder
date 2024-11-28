package com.example.repofinder.data.Remote

import com.example.repofinder.data.Model.Repository

data class RepoSearchResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)
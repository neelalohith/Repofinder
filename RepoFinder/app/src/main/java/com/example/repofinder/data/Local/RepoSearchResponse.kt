package com.example.repofinder.data.Remote

import com.example.repofinder.data.Model.Repository

data class RepoSearchResponseModel(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)
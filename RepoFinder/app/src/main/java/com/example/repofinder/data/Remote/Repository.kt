package com.example.repofinder.data.Remote

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class Repository(
    @PrimaryKey val id: Int,
    val name: String,
    val owner: String,
    val description: String?,
    val html_url: String
)
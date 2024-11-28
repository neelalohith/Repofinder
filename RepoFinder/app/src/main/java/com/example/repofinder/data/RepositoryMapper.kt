package com.example.repofinder.data

import com.example.repofinder.data.Model.Repository as RoomRepository
import com.example.repofinder.data.Remote.Repository as ApiRepository

// Extension function to map ApiRepository (API response) to RoomRepository (Room entity)
fun ApiRepository.toRoomRepository(): RoomRepository {
    return RoomRepository(
        id = this.id,
        name = this.name,
        owner = this.owner,
        description = this.description,
        html_url = this.html_url
    )
}
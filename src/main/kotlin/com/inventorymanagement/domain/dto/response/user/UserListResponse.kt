package com.example.com.inventorymanagement.domain.dto.response.user

import kotlinx.serialization.Serializable

@Serializable
data class UserListResponse(
    val users: List<UserResponse>,
    val total: Long
)

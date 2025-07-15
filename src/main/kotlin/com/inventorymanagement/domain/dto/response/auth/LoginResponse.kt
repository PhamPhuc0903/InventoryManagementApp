package com.example.com.inventorymanagement.domain.dto.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserInfoResponse
)

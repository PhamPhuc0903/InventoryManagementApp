package com.example.com.inventorymanagement.domain.dto.response.auth

import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserInfoResponse(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val userName: String,
    val email: String,
    val firstName: String,
    val lastName: String?,
    val roleName: String
)

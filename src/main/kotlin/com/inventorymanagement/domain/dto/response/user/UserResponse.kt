package com.example.com.inventorymanagement.domain.dto.response.user

import com.example.com.inventorymanagement.utils.serializers.LocalDateSerializer
import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val userName: String,
    val phoneNumber: String,
    val email: String?,
    @Serializable(with = LocalDateSerializer::class)
    val dob: LocalDate?,
    val firstName: String?,
    val lastName: String?,
    val roleName: String,
    val isActive: Boolean
)

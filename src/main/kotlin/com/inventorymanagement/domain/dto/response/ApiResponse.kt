package com.example.com.inventorymanagement.domain.dto.response

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val errorCode: String? = null,
    val timestamp: Long = Instant.now().toEpochMilli()
)

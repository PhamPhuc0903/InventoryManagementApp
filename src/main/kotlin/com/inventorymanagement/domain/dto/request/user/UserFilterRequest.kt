package com.example.com.inventorymanagement.domain.dto.request.user

data class UserFilterRequest(
    val userName: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val roleName: String? = null,
    val isActive: Boolean? = null
)

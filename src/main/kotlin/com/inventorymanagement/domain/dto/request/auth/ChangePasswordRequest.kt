package com.example.com.inventorymanagement.domain.dto.request.auth

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val verifiedPassword: String
)
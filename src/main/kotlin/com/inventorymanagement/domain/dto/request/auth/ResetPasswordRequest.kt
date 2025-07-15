package com.example.com.inventorymanagement.domain.dto.request.auth

data class ResetPasswordRequest(
    val newPassword: String,
    val verifiedPassword: String
)
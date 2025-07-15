package com.example.com.inventorymanagement.domain.dto.request.auth

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)

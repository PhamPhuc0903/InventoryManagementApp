package com.example.com.inventorymanagement.domain.dto.request.auth

import java.time.LocalDate

data class RegisterRequest(
    val userName: String,
    val password: String,
    val phoneNumber: String,
    val email: String? = null,
    val dob: LocalDate? = null,
    val firstName: String? = null,
    val lastName: String? = null
)
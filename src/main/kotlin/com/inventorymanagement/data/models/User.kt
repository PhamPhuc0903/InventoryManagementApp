package com.example.com.inventorymanagement.data.models

import java.util.UUID
import java.time.LocalDate

data class User(
    val userId: UUID,
    val userName: String,
    val password: String,
    val phoneNumber: String,
    val email: String?,
    val dob: LocalDate?,
    val firstName: String?,
    val lastName: String?,
    val roleName: String,
    val isActive: Boolean
)

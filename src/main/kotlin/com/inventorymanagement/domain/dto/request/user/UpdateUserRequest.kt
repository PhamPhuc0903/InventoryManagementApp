package com.example.com.inventorymanagement.domain.dto.request.user

import java.time.LocalDate

data class UpdateUserRequest (
    val userName: String? = null,
    val phoneNumber: String? = null,
    val email: String? = null,
    val dob: LocalDate? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val roleName: String? = null,
    val isActive: Boolean? = null
)
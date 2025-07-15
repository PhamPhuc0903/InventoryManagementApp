package com.example.com.inventorymanagement.data.models

import java.util.UUID
import java.time.LocalDateTime

data class PasswordResetToken(
    val passwordResetId: Int,
    val userId: UUID,
    val contact: String,
    val otp: String,
    val expiredAt: LocalDateTime,
    val isUsed: Boolean,
    val createdAt: LocalDateTime
)

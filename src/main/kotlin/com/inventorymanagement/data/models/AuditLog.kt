package com.example.com.inventorymanagement.data.models

import java.util.UUID
import java.time.LocalDateTime

data class AuditLog(
    val logId: Int,
    val targetTable: String,
    val recordId: String,
    val action: String,
    val oldValues: String?,
    val newValues: String?,
    val changedBy: UUID,
    val changedAt: LocalDateTime
)

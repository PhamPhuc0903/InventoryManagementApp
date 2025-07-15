package com.example.com.inventorymanagement.data.models

import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

data class Invoice(
    val invoiceId: Int,
    val debtReportDetailId: Int,
    val userId: UUID,
    val createdAt: LocalDateTime,
    val totalAmount: BigDecimal,
    val paidAmount: BigDecimal,
    val debtAmount: BigDecimal,
    val updatedAt: LocalDateTime
)

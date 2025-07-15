package com.example.com.inventorymanagement.data.models

import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentReceipt(
    val paymentReceiptId: Int,
    val debtReportDetailId: Int,
    val payerId: UUID,
    val totalAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

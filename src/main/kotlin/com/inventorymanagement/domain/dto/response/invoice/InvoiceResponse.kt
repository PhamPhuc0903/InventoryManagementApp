package com.example.com.inventorymanagement.domain.dto.response.invoice

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import com.example.com.inventorymanagement.utils.serializers.LocalDateTimeSerializer
import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class InvoiceResponse(
    val invoiceId: Int,
    val debtReportDetailId: Int,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = BigDecimalSerializer::class)
    val totalAmount: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val paidAmount: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val debtAmount: BigDecimal,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime
)

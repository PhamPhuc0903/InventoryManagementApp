package com.example.com.inventorymanagement.domain.dto.response.payment

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import com.example.com.inventorymanagement.utils.serializers.LocalDateTimeSerializer
import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class PaymentReceiptResponse(
    val paymentReceiptId: Int,
    val debtReportDetailId: Int,
    @Serializable(with = UUIDSerializer::class)
    val payerId: UUID,
    @Serializable(with = BigDecimalSerializer::class)
    val totalAmount: BigDecimal,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime
)

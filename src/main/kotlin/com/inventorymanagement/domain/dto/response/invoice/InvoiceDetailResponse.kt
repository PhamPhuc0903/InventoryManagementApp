package com.example.com.inventorymanagement.domain.dto.response.invoice

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import com.example.com.inventorymanagement.utils.serializers.LocalDateTimeSerializer
import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceDetailResponse(
    val invoiceId: Int,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = BigDecimalSerializer::class)
    val totalAmount: BigDecimal,
    val items: List<ItemInvoiceResponse>
)


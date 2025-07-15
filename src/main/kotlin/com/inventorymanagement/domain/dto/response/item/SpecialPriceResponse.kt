package com.example.com.inventorymanagement.domain.dto.response.item

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import com.example.com.inventorymanagement.utils.serializers.LocalDateTimeSerializer
import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class SpecialPriceResponse(
    val priceId: Int,
    val itemId: Int,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @Serializable(with = BigDecimalSerializer::class)
    val sellingPrice: BigDecimal,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime
)

package com.example.com.inventorymanagement.domain.dto.response.item

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import com.example.com.inventorymanagement.utils.serializers.LocalDateTimeSerializer
import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import java.math.BigDecimal
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class ItemWithEffectivePriceResponse(
    val itemId: Int,
    val itemName: String,
    val unit: String,
    @Serializable(with = BigDecimalSerializer::class)
    val effectivePrice: BigDecimal,
    val priceId: Int? = null,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime? = null
)


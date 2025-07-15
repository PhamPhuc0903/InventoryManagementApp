package com.example.com.inventorymanagement.domain.dto.response.item

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class ItemResponse(
    val itemId: Int,
    val itemName: String,
    val unit: String,
    @Serializable(with = BigDecimalSerializer::class)
    val defaultPrice: BigDecimal
)

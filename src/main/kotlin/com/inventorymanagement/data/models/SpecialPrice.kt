package com.example.com.inventorymanagement.data.models

import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDateTime

data class SpecialPrice(
    val priceId: Int,
    val itemId: Int,
    val userId: UUID,
    val sellingPrice: BigDecimal,
    val createdAt: LocalDateTime
)

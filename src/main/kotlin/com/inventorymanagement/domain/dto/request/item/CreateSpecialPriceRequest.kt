package com.example.com.inventorymanagement.domain.dto.request.item

import java.math.BigDecimal
import java.util.UUID

data class CreateSpecialPriceRequest(
    val itemId: Int,
    val userId: UUID,
    val sellingPrice: BigDecimal
)


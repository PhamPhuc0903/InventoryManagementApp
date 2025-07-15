package com.example.com.inventorymanagement.domain.dto.request.item

import java.math.BigDecimal

data class CreateItemRequest(
    val itemName: String,
    val unit: String,
    val defaultPrice: BigDecimal
)

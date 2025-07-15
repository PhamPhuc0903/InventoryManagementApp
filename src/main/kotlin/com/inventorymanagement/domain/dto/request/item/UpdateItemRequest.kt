package com.example.com.inventorymanagement.domain.dto.request.item

import java.math.BigDecimal

data class UpdateItemRequest(
    val itemName: String? = null,
    val unit: String? = null,
    val defaultPrice: BigDecimal? = null
)

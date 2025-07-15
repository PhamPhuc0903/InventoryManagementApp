package com.example.com.inventorymanagement.domain.dto.response.item

import kotlinx.serialization.Serializable

@Serializable
data class ItemWithEffectivePriceListResponse(
    val items: List<ItemWithEffectivePriceResponse>,
    val total: Long
)

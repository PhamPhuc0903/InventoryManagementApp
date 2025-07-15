package com.example.com.inventorymanagement.domain.dto.response.item

import kotlinx.serialization.Serializable

@Serializable
data class ItemListResponse(
    val items: List<ItemResponse>,
    val total: Long
)

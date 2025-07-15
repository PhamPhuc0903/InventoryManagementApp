package com.example.com.inventorymanagement.data.models

import java.math.BigDecimal

data class Item (
    val itemId: Int,
    val itemName: String,
    val unit: String,
    val defaultPrice: BigDecimal
)

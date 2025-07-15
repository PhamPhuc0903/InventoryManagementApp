package com.example.com.inventorymanagement.data.models

import java.math.BigDecimal

data class ItemInvoice(
    val invoiceId: Int,
    val itemId: Int,
    val quantity: Int,
    val sellingPrice: BigDecimal
)

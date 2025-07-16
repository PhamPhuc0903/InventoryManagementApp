package com.example.com.inventorymanagement.domain.dto.request.invoice

import java.math.BigDecimal

data class CreateItemInvoiceRequest(
    val itemId: Int,
    val quantity: Int,
    val sellingPrice: BigDecimal
)

package com.example.com.inventorymanagement.domain.dto.request.invoice

import java.math.BigDecimal

data class UpdateItemInvoiceRequest(
    val itemId: Int? = null,
    val quantity: Int? = null,
    val sellingPrice: BigDecimal? = null
)

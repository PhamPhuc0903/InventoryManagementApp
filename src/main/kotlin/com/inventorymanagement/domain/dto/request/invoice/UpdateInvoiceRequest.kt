package com.example.com.inventorymanagement.domain.dto.request.invoice

import java.math.BigDecimal

data class UpdateInvoiceRequest(
    val totalAmount: BigDecimal? = null,
    val paidAmount: BigDecimal? = null,
    val debtAmount: BigDecimal? = null,
)

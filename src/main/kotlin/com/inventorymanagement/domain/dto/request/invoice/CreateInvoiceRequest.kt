package com.example.com.inventorymanagement.domain.dto.request.invoice

import java.math.BigDecimal
import java.util.UUID

data class CreateInvoiceRequest(
    val userId: UUID,
    val totalAmount: BigDecimal,
    val paidAmount: BigDecimal,
    val debtAmount: BigDecimal,
    val itemInvoices: List<CreateItemInvoiceRequest>
)

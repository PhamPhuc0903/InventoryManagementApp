package com.example.com.inventorymanagement.domain.dto.request.payment

import java.math.BigDecimal
import java.util.UUID

data class CreatePaymentReceiptRequest(
    val payerId: UUID,
    val totalAmount: BigDecimal,
)

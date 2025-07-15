package com.example.com.inventorymanagement.domain.dto.request.payment

import java.math.BigDecimal

data class UpdatePaymentReceiptRequest(
    val totalAmount: BigDecimal? = null
)

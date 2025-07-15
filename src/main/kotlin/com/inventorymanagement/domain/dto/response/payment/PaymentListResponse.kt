package com.example.com.inventorymanagement.domain.dto.response.payment

import kotlinx.serialization.Serializable

@Serializable
data class PaymentListResponse(
    val paymentReceipts: List<PaymentReceiptResponse>,
    val total: Long
)

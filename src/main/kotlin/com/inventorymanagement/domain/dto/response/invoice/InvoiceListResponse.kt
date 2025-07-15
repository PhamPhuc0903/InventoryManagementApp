package com.example.com.inventorymanagement.domain.dto.response.invoice

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceListResponse(
    val invoices: List<InvoiceResponse>,
    val total: Long
)

package com.example.com.inventorymanagement.domain.dto.request.invoice

import java.time.LocalDate
import java.util.UUID

data class InvoiceFilterRequest(
    val userId: UUID? = null,
    val date: LocalDate? = null,
)

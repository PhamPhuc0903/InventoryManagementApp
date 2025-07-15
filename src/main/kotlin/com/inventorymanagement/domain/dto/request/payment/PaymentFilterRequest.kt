package com.example.com.inventorymanagement.domain.dto.request.payment

import java.time.LocalDate
import java.util.UUID

data class PaymentFilterRequest(
    val payerId: UUID? = null,
    val date: LocalDate? = null
)

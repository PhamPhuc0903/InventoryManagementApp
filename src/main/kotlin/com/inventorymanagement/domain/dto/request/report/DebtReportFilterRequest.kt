package com.example.com.inventorymanagement.domain.dto.request.report

import java.time.LocalDate
import java.util.UUID

data class DebtReportFilterRequest(
    val userId: UUID? = null,
    val reportMonth: LocalDate? = null
)

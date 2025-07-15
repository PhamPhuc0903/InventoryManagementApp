package com.example.com.inventorymanagement.domain.dto.request.report

import java.time.LocalDate
import java.util.UUID

data class DebtReportDetailFilterRequest(
    val userId: UUID,
    val reportDate: LocalDate? = null
)

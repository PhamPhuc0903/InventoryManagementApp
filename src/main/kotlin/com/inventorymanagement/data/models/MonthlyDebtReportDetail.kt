package com.example.com.inventorymanagement.data.models

import java.time.LocalDateTime
import java.util.UUID

data class MonthlyDebtReportDetail(
    val debtReportDetailId: Int,
    val debtReportId: Int,
    val userId: UUID,
    val reportDate: LocalDateTime
)

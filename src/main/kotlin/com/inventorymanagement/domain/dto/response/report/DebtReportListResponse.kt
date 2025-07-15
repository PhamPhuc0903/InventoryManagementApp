package com.example.com.inventorymanagement.domain.dto.response.report

import kotlinx.serialization.Serializable

@Serializable
data class DebtReportListResponse(
    val monthlyDebtReports: List<MonthlyDebtReportResponse>,
    val total: Long
)

package com.example.com.inventorymanagement.domain.dto.response.report

import kotlinx.serialization.Serializable

@Serializable
data class DebtReportDetailResponse(
    val monthlyDebtReportDetails: List<MonthlyDebtReportDetailResponse>,
    val total: Long
)

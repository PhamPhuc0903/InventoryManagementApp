package com.example.com.inventorymanagement.domain.dto.response.report

import com.example.com.inventorymanagement.utils.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MonthlyDebtReportDetailResponse(
    val debtReportDetailId: Int,
    val debtReportId: Int,
    @Serializable(with = LocalDateTimeSerializer::class)
    val reportDate: LocalDateTime
)

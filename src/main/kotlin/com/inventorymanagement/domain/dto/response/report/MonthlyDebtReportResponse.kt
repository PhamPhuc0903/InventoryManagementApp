package com.example.com.inventorymanagement.domain.dto.response.report

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import com.example.com.inventorymanagement.utils.serializers.LocalDateSerializer
import com.example.com.inventorymanagement.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Serializable
data class MonthlyDebtReportResponse(
    val debtReportId: Int,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @Serializable(with = BigDecimalSerializer::class)
    val openingDebt: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val debtIncrease: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val debtPayment: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val closingDebt: BigDecimal,
    @Serializable(with = LocalDateSerializer::class)
    val reportMonth: LocalDate
)

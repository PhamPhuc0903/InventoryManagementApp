package com.example.com.inventorymanagement.domain.dto.response.report

import com.example.com.inventorymanagement.utils.serializers.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class DebtSummaryResponse(
    @Serializable(with = BigDecimalSerializer::class)
    val totalOpeningDebt: BigDecimal,

    @Serializable(with = BigDecimalSerializer::class)
    val totalDebtIncrease: BigDecimal,

    @Serializable(with = BigDecimalSerializer::class)
    val totalDebtPayment: BigDecimal,

    @Serializable(with = BigDecimalSerializer::class)
    val totalClosingDebt: BigDecimal
)

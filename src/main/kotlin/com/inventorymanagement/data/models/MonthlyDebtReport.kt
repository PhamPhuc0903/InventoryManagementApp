package com.example.com.inventorymanagement.data.models

import java.util.UUID
import java.math.BigDecimal
import java.time.LocalDate

data class MonthlyDebtReport(
    val debtReportId: Int,
    val userId: UUID,
    val openingDebt: BigDecimal,
    val debtIncrease: BigDecimal,
    val debtPayment: BigDecimal,
    val closingDebt: BigDecimal,
    val reportMonth: LocalDate
)

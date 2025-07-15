package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.math.BigDecimal
import java.time.LocalDate

object MonthlyDebtReportTable: Table("monthly_debt_reports") {
    val debtReportId = integer("debt_report_id").autoIncrement()
    val userId = reference("user_id", UserTable.userId)
    val openingDebt = decimal("opening_debt",18,2).default(BigDecimal.ZERO)
    val debtIncrease = decimal("debt_increase",18,2).default(BigDecimal.ZERO)
    val debtPayment = decimal("debt_payment",18,2).default(BigDecimal.ZERO)
    val closingDebt = decimal("closing_debt",18,2).default(BigDecimal.ZERO)
    val reportMonth = date("report_month").clientDefault { LocalDate.now() }
    override val primaryKey = PrimaryKey((debtReportId))
}
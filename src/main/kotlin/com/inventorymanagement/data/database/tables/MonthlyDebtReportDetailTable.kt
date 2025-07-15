package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object MonthlyDebtReportDetailTable: Table("monthly_debt_report_details") {
    val debtReportDetailId = integer("debt_report_detail_id").autoIncrement()
    val debtReportId = reference("debt_report_id", MonthlyDebtReportTable.debtReportId)
    val userId = reference("user_id", UserTable.userId)
    val reportDate = datetime("report_date").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(debtReportDetailId)
}
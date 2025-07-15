package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.math.BigDecimal
import java.time.LocalDateTime

object InvoiceTable: Table("invoices") {
    val invoiceId = integer("invoice_id").autoIncrement()
    val debtReportDetailId = reference("debt_report_detail_id", MonthlyDebtReportDetailTable.debtReportDetailId).uniqueIndex()
    val userId = reference("user_id", UserTable.userId)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val totalAmount = decimal("total_amount",18,2).default(BigDecimal.ZERO)
    val paidAmount = decimal("paid_amount",18,2).default(BigDecimal.ZERO)
    val debtAmount = decimal("debt_amount",18,2).default(BigDecimal.ZERO)
    val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(invoiceId)
}
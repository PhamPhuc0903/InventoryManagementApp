package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.math.BigDecimal
import java.time.LocalDateTime

object PaymentReceiptTable: Table("payment_receipts") {
    val paymentReceiptId = integer("payment_receipt_id").autoIncrement()
    val debtReportDetailId = reference("debt_report_detail_id", MonthlyDebtReportDetailTable.debtReportDetailId).uniqueIndex()
    val payerId = reference("payer_id", UserTable.userId)
    val totalAmount = decimal("total_amount",18,2).default(BigDecimal.ZERO)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
    override val primaryKey = PrimaryKey(paymentReceiptId)
}
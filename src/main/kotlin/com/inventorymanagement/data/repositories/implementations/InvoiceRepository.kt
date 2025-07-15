package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.InvoiceTable
import com.example.com.inventorymanagement.data.models.Invoice
import com.example.com.inventorymanagement.data.repositories.interfaces.IInvoiceRepository
import com.example.com.inventorymanagement.domain.dto.request.invoice.InvoiceFilterRequest
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import java.time.LocalDateTime
import kotlin.Int

class InvoiceRepository: IInvoiceRepository {
    private fun rowToInvoice(row: ResultRow): Invoice = Invoice(
        invoiceId = row[InvoiceTable.invoiceId],
        debtReportDetailId = row[InvoiceTable.debtReportDetailId],
        userId = row[InvoiceTable.userId],
        createdAt = row[InvoiceTable.createdAt],
        totalAmount = row[InvoiceTable.totalAmount],
        paidAmount = row[InvoiceTable.paidAmount],
        debtAmount = row[InvoiceTable.debtAmount],
        updatedAt = row[InvoiceTable.updatedAt]
    )

    override suspend fun getAll(): List<Invoice> = newSuspendedTransaction(Dispatchers.IO) {
        InvoiceTable.selectAll().map { rowToInvoice(it) }
    }

    override suspend fun getById(invoiceId: Int): Invoice? = newSuspendedTransaction(Dispatchers.IO) {
        InvoiceTable.selectAll().where { InvoiceTable.invoiceId eq invoiceId}.map { rowToInvoice(it) }.singleOrNull()
    }

    override suspend fun getByDebtReportDetailId(debtReportDetailId: Int): Invoice? = newSuspendedTransaction(
        Dispatchers.IO) {
        InvoiceTable.selectAll().where{InvoiceTable.debtReportDetailId eq debtReportDetailId}.map { rowToInvoice(it) }.singleOrNull()
    }

    override suspend fun getByUserId(userId: UUID): List<Invoice> = newSuspendedTransaction(
        Dispatchers.IO) {
        InvoiceTable.selectAll().where{InvoiceTable.userId eq userId}.map { rowToInvoice(it) }
    }

    override suspend fun filterInvoice(filter: InvoiceFilterRequest): List<Invoice> = newSuspendedTransaction(Dispatchers.IO) {
        val conditions = buildList<Op<Boolean>> {
            filter.userId?.let {
                add(InvoiceTable.userId eq it)
            }

            filter.date?.let { date ->
                val startOfDay = date.atStartOfDay()
                val endOfDay = date.atTime(23, 59, 59, 999_999_999)
                add(InvoiceTable.createdAt greaterEq startOfDay)
                add(InvoiceTable.createdAt lessEq endOfDay)
            }
        }

        val query = if (conditions.isNotEmpty()) {
            InvoiceTable.selectAll().where { conditions.reduce { acc, op -> acc and op } }
        } else {
            InvoiceTable.selectAll()
        }

        query.map { rowToInvoice(it) }
    }

    override suspend fun create(invoice: Invoice): Invoice? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = InvoiceTable.insert {
            it[debtReportDetailId] = invoice.debtReportDetailId
            it[userId] = invoice.userId
            it[createdAt] = invoice.createdAt
            it[totalAmount] = invoice.totalAmount
            it[paidAmount] = invoice.paidAmount
            it[debtAmount] = invoice.debtAmount
            it[updatedAt] = invoice.updatedAt
        }
        InvoiceTable.selectAll().where { InvoiceTable.debtReportDetailId eq invoice.debtReportDetailId }.map { rowToInvoice(it) }.singleOrNull()
    }

    override suspend fun update(invoice: Invoice): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val updatedRows = InvoiceTable.update({ InvoiceTable.invoiceId eq invoice.invoiceId}) {
            it[debtReportDetailId] = invoice.debtReportDetailId
            it[userId] = invoice.userId
            it[createdAt] = invoice.createdAt
            it[totalAmount] = invoice.totalAmount
            it[paidAmount] = invoice.paidAmount
            it[debtAmount] = invoice.debtAmount
            it[updatedAt] = invoice.updatedAt
        }
        updatedRows > 0
    }

     override suspend fun delete(invoiceId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
         InvoiceTable.deleteWhere {InvoiceTable.invoiceId eq invoiceId} > 0
     }
}
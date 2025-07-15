package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.PaymentReceiptTable
import com.example.com.inventorymanagement.data.models.PaymentReceipt
import com.example.com.inventorymanagement.data.repositories.interfaces.IPaymentReceiptRepository
import com.example.com.inventorymanagement.domain.dto.request.payment.PaymentFilterRequest
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class PaymentReceiptRepository : IPaymentReceiptRepository {

    private fun rowToPaymentReceipt(row: ResultRow): PaymentReceipt = PaymentReceipt(
        paymentReceiptId = row[PaymentReceiptTable.paymentReceiptId],
        debtReportDetailId = row[PaymentReceiptTable.debtReportDetailId],
        payerId = row[PaymentReceiptTable.payerId],
        totalAmount = row[PaymentReceiptTable.totalAmount],
        createdAt = row[PaymentReceiptTable.createdAt],
        updatedAt = row[PaymentReceiptTable.updatedAt]
    )

    override suspend fun getAll(): List<PaymentReceipt> = newSuspendedTransaction(Dispatchers.IO) {
        PaymentReceiptTable.selectAll().map { rowToPaymentReceipt(it) }
    }

    override suspend fun getById(paymentReceiptId: Int): PaymentReceipt? = newSuspendedTransaction(Dispatchers.IO) {
        PaymentReceiptTable
            .selectAll()
            .where { PaymentReceiptTable.paymentReceiptId eq paymentReceiptId }
            .map { rowToPaymentReceipt(it) }
            .singleOrNull()
    }

    override suspend fun getByDebtReportDetailId(debtReportDetailId: Int): PaymentReceipt? = newSuspendedTransaction(Dispatchers.IO) {
        PaymentReceiptTable
            .selectAll()
            .where { PaymentReceiptTable.debtReportDetailId eq debtReportDetailId }
            .map { rowToPaymentReceipt(it) }
            .singleOrNull()
    }

    override suspend fun getByPayerId(payerId: UUID): List<PaymentReceipt> = newSuspendedTransaction(Dispatchers.IO) {
        PaymentReceiptTable
            .selectAll()
            .where { PaymentReceiptTable.payerId eq payerId }
            .map { rowToPaymentReceipt(it) }
    }

    override suspend fun filterPayment(filter: PaymentFilterRequest): List<PaymentReceipt> = newSuspendedTransaction(Dispatchers.IO) {
        val conditions = buildList<Op<Boolean>> {
            filter.payerId?.let {
                add(PaymentReceiptTable.payerId eq it)
            }

            filter.date?.let { date ->
                val startOfDay = date.atStartOfDay()
                val endOfDay = date.atTime(23, 59, 59, 999_999_999)
                add(PaymentReceiptTable.createdAt greaterEq startOfDay)
                add(PaymentReceiptTable.createdAt lessEq endOfDay)
            }
        }

        val query = if (conditions.isNotEmpty()) {
            PaymentReceiptTable.selectAll().where { conditions.reduce { acc, op -> acc and op } }
        } else {
            PaymentReceiptTable.selectAll()
        }

        query.map { rowToPaymentReceipt(it) }
    }


    override suspend fun create(paymentReceipt: PaymentReceipt): PaymentReceipt? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = PaymentReceiptTable.insert {
            it[debtReportDetailId] = paymentReceipt.debtReportDetailId
            it[payerId] = paymentReceipt.payerId
            it[totalAmount] = paymentReceipt.totalAmount
            it[createdAt] = paymentReceipt.createdAt
            it[updatedAt] = paymentReceipt.updatedAt
        }

        PaymentReceiptTable
            .selectAll()
            .where {
                (PaymentReceiptTable.debtReportDetailId eq paymentReceipt.debtReportDetailId)
            }
            .map { rowToPaymentReceipt(it) }
            .singleOrNull()
    }

    override suspend fun update(paymentReceipt: PaymentReceipt): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PaymentReceiptTable.update({
            PaymentReceiptTable.paymentReceiptId eq paymentReceipt.paymentReceiptId
        }) {
            it[debtReportDetailId] = paymentReceipt.debtReportDetailId
            it[payerId] = paymentReceipt.payerId
            it[totalAmount] = paymentReceipt.totalAmount
            it[createdAt] = paymentReceipt.createdAt
            it[updatedAt] = paymentReceipt.updatedAt
        } > 0
    }

    override suspend fun delete(paymentReceiptId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PaymentReceiptTable.deleteWhere {
            PaymentReceiptTable.paymentReceiptId eq paymentReceiptId
        } > 0
    }
}

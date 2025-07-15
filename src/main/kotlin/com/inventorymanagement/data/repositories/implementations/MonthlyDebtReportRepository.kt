package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.MonthlyDebtReportTable
import com.example.com.inventorymanagement.data.models.MonthlyDebtReport
import com.example.com.inventorymanagement.data.repositories.interfaces.IMonthlyDebtReportRepository
import com.example.com.inventorymanagement.domain.dto.request.report.DebtReportFilterRequest
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDate
import java.util.UUID

class MonthlyDebtReportRepository : IMonthlyDebtReportRepository {

    private fun rowToMonthlyDebtReport(row: ResultRow): MonthlyDebtReport = MonthlyDebtReport(
        debtReportId = row[MonthlyDebtReportTable.debtReportId],
        userId = row[MonthlyDebtReportTable.userId],
        openingDebt = row[MonthlyDebtReportTable.openingDebt],
        debtIncrease = row[MonthlyDebtReportTable.debtIncrease],
        debtPayment = row[MonthlyDebtReportTable.debtPayment],
        closingDebt = row[MonthlyDebtReportTable.closingDebt],
        reportMonth = row[MonthlyDebtReportTable.reportMonth]
    )

    override suspend fun getAll(): List<MonthlyDebtReport> = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportTable.selectAll().map { rowToMonthlyDebtReport(it) }
    }

    override suspend fun getById(debtReportId: Int): MonthlyDebtReport? = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportTable.selectAll().where { MonthlyDebtReportTable.debtReportId eq debtReportId }
            .map { rowToMonthlyDebtReport(it) }
            .singleOrNull()
    }

    override suspend fun getByUserId(userId: UUID): List<MonthlyDebtReport> = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportTable.selectAll().where { MonthlyDebtReportTable.userId eq userId }
            .map { rowToMonthlyDebtReport(it) }
    }

    override suspend fun getByMonth(reportMonth: LocalDate): List<MonthlyDebtReport> = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportTable.selectAll().where { MonthlyDebtReportTable.reportMonth eq reportMonth }
            .map { rowToMonthlyDebtReport(it) }
    }

    override suspend fun getByUserIdAndMonth(userId: UUID, reportMonth: LocalDate): MonthlyDebtReport? = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportTable
            .selectAll().where {
                (MonthlyDebtReportTable.userId eq userId) and
                        (MonthlyDebtReportTable.reportMonth eq reportMonth)
            }
            .map { rowToMonthlyDebtReport(it) }
            .singleOrNull()
    }

    override suspend fun filterDebtReports(filter: DebtReportFilterRequest): List<MonthlyDebtReport> = newSuspendedTransaction(Dispatchers.IO) {
        val conditions = buildList<Op<Boolean>> {
            filter.userId?.let {
                add(MonthlyDebtReportTable.userId eq it)
            }

            filter.reportMonth?.let {
                add(MonthlyDebtReportTable.reportMonth eq it.withDayOfMonth(1))
            }
        }

        val query = if (conditions.isNotEmpty()) {
            MonthlyDebtReportTable.selectAll().where { conditions.reduce { acc, op -> acc and op } }
        } else {
            MonthlyDebtReportTable.selectAll()
        }

        query.map { rowToMonthlyDebtReport(it) }
    }



    override suspend fun create(monthlyDebtReport: MonthlyDebtReport): MonthlyDebtReport? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = MonthlyDebtReportTable.insert {
            it[userId] = monthlyDebtReport.userId
            it[openingDebt] = monthlyDebtReport.openingDebt
            it[debtIncrease] = monthlyDebtReport.debtIncrease
            it[debtPayment] = monthlyDebtReport.debtPayment
            it[closingDebt] = monthlyDebtReport.closingDebt
            it[reportMonth] = monthlyDebtReport.reportMonth
        }
        MonthlyDebtReportTable.selectAll().where { (MonthlyDebtReportTable.userId eq monthlyDebtReport.userId) and (MonthlyDebtReportTable.reportMonth eq monthlyDebtReport.reportMonth) }.map { rowToMonthlyDebtReport(it) }.singleOrNull()
    }

    override suspend fun update(monthlyDebtReport: MonthlyDebtReport): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportTable.update({ MonthlyDebtReportTable.debtReportId eq monthlyDebtReport.debtReportId }) {
            it[userId] = monthlyDebtReport.userId
            it[openingDebt] = monthlyDebtReport.openingDebt
            it[debtIncrease] = monthlyDebtReport.debtIncrease
            it[debtPayment] = monthlyDebtReport.debtPayment
            it[closingDebt] = monthlyDebtReport.closingDebt
            it[reportMonth] = monthlyDebtReport.reportMonth
        } > 0
    }

    override suspend fun delete(debtReportId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportTable.deleteWhere { MonthlyDebtReportTable.debtReportId eq debtReportId } > 0
    }
}

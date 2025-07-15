package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.MonthlyDebtReportDetailTable
import com.example.com.inventorymanagement.data.models.MonthlyDebtReportDetail
import com.example.com.inventorymanagement.data.repositories.interfaces.IMonthlyDebtReportDetailRepository
import com.example.com.inventorymanagement.domain.dto.request.report.DebtReportDetailFilterRequest
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDate
import java.util.UUID

class MonthlyDebtReportDetailRepository : IMonthlyDebtReportDetailRepository {

    private fun rowToDetail(row: ResultRow): MonthlyDebtReportDetail = MonthlyDebtReportDetail(
        debtReportDetailId = row[MonthlyDebtReportDetailTable.debtReportDetailId],
        debtReportId = row[MonthlyDebtReportDetailTable.debtReportId],
        userId = row[MonthlyDebtReportDetailTable.userId],
        reportDate = row[MonthlyDebtReportDetailTable.reportDate]
    )

    override suspend fun getAll(): List<MonthlyDebtReportDetail> = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportDetailTable.selectAll().map { rowToDetail(it) }
    }

    override suspend fun getById(debtReportDetailId: Int): MonthlyDebtReportDetail? = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportDetailTable
            .selectAll()
            .where { MonthlyDebtReportDetailTable.debtReportDetailId eq debtReportDetailId }
            .map { rowToDetail(it) }
            .singleOrNull()
    }

    override suspend fun getByDebtReportId(debtReportId: Int): List<MonthlyDebtReportDetail> = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportDetailTable
            .selectAll()
            .where { MonthlyDebtReportDetailTable.debtReportId eq debtReportId }
            .map { rowToDetail(it) }
    }

    override suspend fun getByUserIdAndMonth(userId: UUID, reportMonth: LocalDate): List<MonthlyDebtReportDetail> =
        newSuspendedTransaction(Dispatchers.IO) {
            val startOfMonth = reportMonth.withDayOfMonth(1).atStartOfDay()
            val endOfMonth = reportMonth.withDayOfMonth(reportMonth.lengthOfMonth())
                .atTime(23, 59, 59, 999_999_999)

            MonthlyDebtReportDetailTable
                .selectAll()
                .where {
                    (MonthlyDebtReportDetailTable.userId eq userId) and
                            (MonthlyDebtReportDetailTable.reportDate greaterEq startOfMonth) and
                            (MonthlyDebtReportDetailTable.reportDate lessEq endOfMonth)
                }
                .map { rowToDetail(it) }
        }


    override suspend fun filterByDate(filter: DebtReportDetailFilterRequest): List<MonthlyDebtReportDetail> =
        newSuspendedTransaction(Dispatchers.IO) {
            val conditions = buildList<Op<Boolean>> {
                filter.userId?.let {userId ->
                    add(MonthlyDebtReportDetailTable.userId eq userId)
                }
                filter.reportDate?.let { date ->
                    val startOfDay = date.atStartOfDay()
                    val endOfDay = date.atTime(23, 59, 59, 999_999_999)

                    add(MonthlyDebtReportDetailTable.reportDate greaterEq startOfDay)
                    add(MonthlyDebtReportDetailTable.reportDate lessEq endOfDay)
                }
            }

            val query = if (conditions.isNotEmpty()) {
                MonthlyDebtReportDetailTable.selectAll().where {
                    conditions.reduce { acc, op -> acc and op }
                }
            } else {
                MonthlyDebtReportDetailTable.selectAll()
            }

            query.map { rowToDetail(it) }
        }



    override suspend fun create(monthlyDebtReportDetail: MonthlyDebtReportDetail): MonthlyDebtReportDetail? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = MonthlyDebtReportDetailTable.insert {
            it[debtReportId] = monthlyDebtReportDetail.debtReportId
            it[userId] = monthlyDebtReportDetail.userId
            it[reportDate] = monthlyDebtReportDetail.reportDate
        }
        MonthlyDebtReportDetailTable
            .selectAll()
            .where {
                (MonthlyDebtReportDetailTable.debtReportId eq monthlyDebtReportDetail.debtReportId) and
                        (MonthlyDebtReportDetailTable.reportDate eq monthlyDebtReportDetail.reportDate)
            }
            .map { rowToDetail(it) }
            .singleOrNull()
    }

    override suspend fun update(monthlyDebtReportDetail: MonthlyDebtReportDetail): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportDetailTable.update({
            MonthlyDebtReportDetailTable.debtReportDetailId eq monthlyDebtReportDetail.debtReportDetailId
        }) {
            it[debtReportId] = monthlyDebtReportDetail.debtReportId
            it[userId] = monthlyDebtReportDetail.userId
            it[reportDate] = monthlyDebtReportDetail.reportDate
        } > 0
    }

    override suspend fun delete(debtReportDetailId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        MonthlyDebtReportDetailTable.deleteWhere {
            MonthlyDebtReportDetailTable.debtReportDetailId eq debtReportDetailId
        } > 0
    }
}

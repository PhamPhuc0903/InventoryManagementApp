package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.MonthlyDebtReportDetail
import com.example.com.inventorymanagement.domain.dto.request.report.DebtReportDetailFilterRequest
import java.time.LocalDate
import java.util.UUID

interface IMonthlyDebtReportDetailRepository {
    suspend fun getAll(): List<MonthlyDebtReportDetail>
    suspend fun getById(debtReportDetailId: Int): MonthlyDebtReportDetail?
    suspend fun getByDebtReportId(debtReportId: Int): List<MonthlyDebtReportDetail>

    suspend fun getByUserIdAndMonth(userId: UUID, reportMonth: LocalDate): List<MonthlyDebtReportDetail>

    suspend fun filterByDate(filter: DebtReportDetailFilterRequest): List<MonthlyDebtReportDetail>
    suspend fun create(monthlyDebtReportDetail: MonthlyDebtReportDetail): MonthlyDebtReportDetail?
    suspend fun update(monthlyDebtReportDetail: MonthlyDebtReportDetail): Boolean
    suspend fun delete(debtReportDetailId: Int): Boolean
}
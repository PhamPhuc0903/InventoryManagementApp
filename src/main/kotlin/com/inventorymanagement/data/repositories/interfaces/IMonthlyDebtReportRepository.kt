package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.MonthlyDebtReport
import com.example.com.inventorymanagement.domain.dto.request.report.DebtReportFilterRequest
import java.time.LocalDate
import java.util.UUID

interface IMonthlyDebtReportRepository {
    suspend fun getAll(): List<MonthlyDebtReport>
    suspend fun getById(debtReportId: Int): MonthlyDebtReport?
    suspend fun getByUserId(userId: UUID): List<MonthlyDebtReport>
    suspend fun getByMonth(reportMonth: LocalDate): List<MonthlyDebtReport>
    suspend fun getByUserIdAndMonth(userId: UUID, reportMonth: LocalDate): MonthlyDebtReport?

    suspend fun filterDebtReports(filter: DebtReportFilterRequest): List<MonthlyDebtReport>
    suspend fun create(monthlyDebtReport: MonthlyDebtReport): MonthlyDebtReport?
    suspend fun update(monthlyDebtReport: MonthlyDebtReport): Boolean
    suspend fun delete(debtReportId: Int): Boolean
}
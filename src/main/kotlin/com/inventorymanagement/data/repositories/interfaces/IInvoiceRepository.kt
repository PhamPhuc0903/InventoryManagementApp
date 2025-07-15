package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.Invoice
import com.example.com.inventorymanagement.domain.dto.request.invoice.InvoiceFilterRequest
import java.util.UUID

interface IInvoiceRepository {
    suspend fun getAll(): List<Invoice>
    suspend fun getById(invoiceId: Int): Invoice?
    suspend fun getByDebtReportDetailId(debtReportDetailId: Int): Invoice?
    suspend fun getByUserId(userId: UUID): List<Invoice>

    suspend fun filterInvoice(filter: InvoiceFilterRequest): List<Invoice>
    suspend fun create(invoice: Invoice): Invoice?
    suspend fun update(invoice: Invoice): Boolean
    suspend fun delete(invoiceId: Int): Boolean
}
package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.InvoiceTable
import com.example.com.inventorymanagement.domain.dto.request.invoice.CreateInvoiceRequest
import com.example.com.inventorymanagement.data.models.Invoice
import com.example.com.inventorymanagement.domain.dto.request.invoice.InvoiceFilterRequest
import com.example.com.inventorymanagement.domain.dto.request.invoice.UpdateInvoiceRequest
import com.example.com.inventorymanagement.domain.dto.response.invoice.InvoiceListResponse
import com.example.com.inventorymanagement.domain.dto.response.invoice.InvoiceResponse
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import java.time.LocalDateTime
import kotlin.collections.buildList


object InvoiceMapper {
    fun toInvoice(request: CreateInvoiceRequest): Invoice = Invoice(
        invoiceId =  0,
        debtReportDetailId = 0,
        userId = request.userId,
        createdAt = LocalDateTime.now(),
        totalAmount = request.totalAmount,
        paidAmount = request.paidAmount,
        debtAmount = request.debtAmount,
        updatedAt = LocalDateTime.now()
    )

    fun toInvoice(request: UpdateInvoiceRequest, existingInvoice: Invoice): Invoice = existingInvoice.copy(
        totalAmount = request.totalAmount?: existingInvoice.totalAmount,
        paidAmount = request.paidAmount?: existingInvoice.paidAmount,
        debtAmount = request.debtAmount?: existingInvoice.debtAmount,
    )

    fun InvoiceFilterRequest.toFilterConditions(): List<Op<Boolean>> = buildList {
        userId?.let {
            add(InvoiceTable.userId eq it)
        }

        date?.let { date ->
            val startOfDay = date.atStartOfDay()
            val endOfDay = date.atTime(23, 59, 59, 999_999_999)
            add(InvoiceTable.createdAt greaterEq startOfDay)
            add(InvoiceTable.createdAt lessEq endOfDay)
        }
    }

    fun Invoice.toInvoiceResponse(): InvoiceResponse = InvoiceResponse(
        invoiceId = this.invoiceId,
        debtReportDetailId = this.debtReportDetailId,
        userId = this.userId,
        createdAt = this.createdAt,
        totalAmount = this.totalAmount,
        paidAmount = this.paidAmount,
        debtAmount = this.debtAmount,
        updatedAt = this.updatedAt
    )

    fun toInvoiceListResponse(invoices: List<Invoice>, total: Long): InvoiceListResponse =
        InvoiceListResponse(
            invoices = invoices.map { it.toInvoiceResponse() },
            total = total
        )


}
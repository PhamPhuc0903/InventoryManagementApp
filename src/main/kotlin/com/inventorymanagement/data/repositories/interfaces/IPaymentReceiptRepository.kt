package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.PaymentReceipt
import com.example.com.inventorymanagement.domain.dto.request.payment.PaymentFilterRequest
import java.util.UUID

interface IPaymentReceiptRepository {
    suspend fun getAll(): List<PaymentReceipt>
    suspend fun getById(paymentReceiptId: Int): PaymentReceipt?
    suspend fun getByDebtReportDetailId(debtReportDetailId: Int): PaymentReceipt?
    suspend fun getByPayerId(payerId: UUID): List<PaymentReceipt>

    suspend fun filterPayment(filter: PaymentFilterRequest): List<PaymentReceipt>
    suspend fun create(paymentReceipt: PaymentReceipt): PaymentReceipt?
    suspend fun update(paymentReceipt: PaymentReceipt): Boolean
    suspend fun delete(paymentReceiptId: Int): Boolean
}
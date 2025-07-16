package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.ItemInvoiceTable
import com.example.com.inventorymanagement.data.models.ItemInvoice
import com.example.com.inventorymanagement.domain.dto.request.invoice.CreateItemInvoiceRequest
import com.example.com.inventorymanagement.domain.dto.request.invoice.UpdateItemInvoiceRequest
import com.example.com.inventorymanagement.domain.dto.response.invoice.ItemInvoiceResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.BatchInsertStatement
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.math.BigDecimal

object ItemInvoiceMapper {
    fun toItemInvoice(request: CreateItemInvoiceRequest): ItemInvoice = ItemInvoice(
        invoiceId = 0,
        itemId = request.itemId,
        quantity = request.quantity,
        sellingPrice = request.sellingPrice,
        total = BigDecimal.ZERO
    )

    fun toItemInvoice(request: UpdateItemInvoiceRequest, existingItemInvoice: ItemInvoice): ItemInvoice = existingItemInvoice.copy(
        itemId = request.itemId?: existingItemInvoice.itemId,
        quantity = request.quantity?: existingItemInvoice.quantity,
        sellingPrice = request.sellingPrice?: existingItemInvoice.sellingPrice
    )

    fun ItemInvoice.ItemInvoiceResponse(): ItemInvoiceResponse = ItemInvoiceResponse(
        itemId = this.itemId,
        quantity = this.quantity,
        sellingPrice = this.sellingPrice,
        total = this.total
    )

    fun ResultRow.toItemInvoice(): ItemInvoice = ItemInvoice(
        invoiceId = this[ItemInvoiceTable.invoiceId],
        itemId = this[ItemInvoiceTable.itemId],
        quantity = this[ItemInvoiceTable.quantity],
        sellingPrice = this[ItemInvoiceTable.sellingPrice],
        total = this[ItemInvoiceTable.total]
    )

    fun BatchInsertStatement.toItemInvoiceTable(item: ItemInvoice) {
        this[ItemInvoiceTable.invoiceId] = item.invoiceId
        this[ItemInvoiceTable.itemId] = item.itemId
        this[ItemInvoiceTable.quantity] = item.quantity
        this[ItemInvoiceTable.sellingPrice] = item.sellingPrice
        this[ItemInvoiceTable.total] = item.total
    }



    fun  UpdateStatement.toItemInvoiceTable(itemInvoice: ItemInvoice) {
        this[ItemInvoiceTable.quantity] = itemInvoice.quantity
        this[ItemInvoiceTable.sellingPrice] = itemInvoice.sellingPrice
        this[ItemInvoiceTable.total] = itemInvoice.total
    }
}
package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

object ItemInvoiceTable: Table("items_invoices") {
    val invoiceId = reference("invoice_id", InvoiceTable.invoiceId)
    val itemId = reference("item_id", ItemTable.itemId)
    val quantity = integer("quantity")
    val sellingPrice = decimal("selling_price",18,2).default(BigDecimal.ZERO)
    override val primaryKey = PrimaryKey(invoiceId,itemId)
}
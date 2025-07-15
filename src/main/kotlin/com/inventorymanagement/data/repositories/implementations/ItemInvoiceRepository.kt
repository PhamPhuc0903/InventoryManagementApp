package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.ItemInvoiceTable
import com.example.com.inventorymanagement.data.models.ItemInvoice
import com.example.com.inventorymanagement.data.repositories.interfaces.IItemInvoiceRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.math.BigDecimal

class ItemInvoiceRepository : IItemInvoiceRepository {

    private fun rowToItemInvoice(row: ResultRow): ItemInvoice = ItemInvoice(
        invoiceId = row[ItemInvoiceTable.invoiceId],
        itemId = row[ItemInvoiceTable.itemId],
        quantity = row[ItemInvoiceTable.quantity],
        sellingPrice = row[ItemInvoiceTable.sellingPrice]
    )

    override suspend fun getAll(): List<ItemInvoice> = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.selectAll().map { rowToItemInvoice(it) }
    }

    override suspend fun getByInvoiceId(invoiceId: Int): List<ItemInvoice> = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.selectAll().where { ItemInvoiceTable.invoiceId eq invoiceId }.map { rowToItemInvoice(it) }
    }

    override suspend fun getByItemId(itemId: Int): List<ItemInvoice> = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.selectAll().where { ItemInvoiceTable.itemId eq itemId }.map { rowToItemInvoice(it) }
    }

    override suspend fun getByInvoiceIdAndItemId(invoiceId: Int, itemId: Int): ItemInvoice? = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable
            .selectAll().where { (ItemInvoiceTable.invoiceId eq invoiceId) and (ItemInvoiceTable.itemId eq itemId) }
            .map { rowToItemInvoice(it) }
            .singleOrNull()
    }

    override suspend fun create(itemInvoice: ItemInvoice): ItemInvoice? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = ItemInvoiceTable.insert {
            it[invoiceId] = itemInvoice.invoiceId
            it[itemId] = itemInvoice.itemId
            it[quantity] = itemInvoice.quantity
            it[sellingPrice] = itemInvoice.sellingPrice
        }
        ItemInvoiceTable.selectAll().where { (ItemInvoiceTable.invoiceId eq itemInvoice.invoiceId) and (ItemInvoiceTable.itemId eq itemInvoice.itemId) }.map { rowToItemInvoice(it) }.singleOrNull()
    }

    override suspend fun update(itemInvoice: ItemInvoice): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val updatedRows = ItemInvoiceTable.update({
            (ItemInvoiceTable.invoiceId eq itemInvoice.invoiceId) and
                    (ItemInvoiceTable.itemId eq itemInvoice.itemId)
        }) {
            it[quantity] = itemInvoice.quantity
            it[sellingPrice] = itemInvoice.sellingPrice
        }
        updatedRows > 0
    }

    override suspend fun delete(invoiceId: Int, itemId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.deleteWhere {
            (ItemInvoiceTable.invoiceId eq invoiceId) and
                    (ItemInvoiceTable.itemId eq itemId)
        } > 0
    }
}

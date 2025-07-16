package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.ItemInvoiceTable
import com.example.com.inventorymanagement.data.models.ItemInvoice
import com.example.com.inventorymanagement.data.repositories.interfaces.IItemInvoiceRepository
import com.example.com.inventorymanagement.domain.mappers.ItemInvoiceMapper.toItemInvoice
import com.example.com.inventorymanagement.domain.mappers.ItemInvoiceMapper.toItemInvoiceTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ItemInvoiceRepository : IItemInvoiceRepository {

    override suspend fun getAll(): List<ItemInvoice> = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.selectAll().map { it.toItemInvoice() }
    }

    override suspend fun getByInvoiceId(invoiceId: Int): List<ItemInvoice> = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.selectAll().where { ItemInvoiceTable.invoiceId eq invoiceId }.map { it.toItemInvoice() }
    }

    override suspend fun getByItemId(itemId: Int): List<ItemInvoice> = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.selectAll().where { ItemInvoiceTable.itemId eq itemId }.map { it.toItemInvoice() }
    }

    override suspend fun getByInvoiceIdAndItemId(invoiceId: Int, itemId: Int): ItemInvoice? = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable
            .selectAll().where { (ItemInvoiceTable.invoiceId eq invoiceId) and (ItemInvoiceTable.itemId eq itemId) }
            .map { it.toItemInvoice() }
            .singleOrNull()
    }

    override suspend fun createMany(items: List<ItemInvoice>): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.batchInsert(items) { item ->
            toItemInvoiceTable(item)
        }
        true
    }


    override suspend fun update(itemInvoice: ItemInvoice): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val updatedRows = ItemInvoiceTable.update({
            (ItemInvoiceTable.invoiceId eq itemInvoice.invoiceId) and
                    (ItemInvoiceTable.itemId eq itemInvoice.itemId)
        }) { it.toItemInvoiceTable(itemInvoice)}
        updatedRows > 0
    }

    override suspend fun delete(invoiceId: Int, itemId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        ItemInvoiceTable.deleteWhere {
            (ItemInvoiceTable.invoiceId eq invoiceId) and
                    (ItemInvoiceTable.itemId eq itemId)
        } > 0
    }
}

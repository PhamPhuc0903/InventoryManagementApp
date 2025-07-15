package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.ItemInvoice

interface IItemInvoiceRepository {
    suspend fun getAll(): List<ItemInvoice>
    suspend fun getByInvoiceId(invoiceId: Int): List<ItemInvoice>
    suspend fun getByItemId(itemId: Int): List<ItemInvoice>
    suspend fun getByInvoiceIdAndItemId(invoiceId: Int, itemId: Int): ItemInvoice?
    suspend fun create(itemInvoice: ItemInvoice): ItemInvoice?
    suspend fun update(itemInvoice: ItemInvoice): Boolean
    suspend fun delete(invoiceId: Int, itemId: Int): Boolean
}

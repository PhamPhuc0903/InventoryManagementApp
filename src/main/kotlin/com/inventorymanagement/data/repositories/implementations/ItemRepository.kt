package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.ItemTable
import com.example.com.inventorymanagement.data.models.Item
import com.example.com.inventorymanagement.data.repositories.interfaces.IItemRepository
import com.example.com.inventorymanagement.domain.mappers.ItemMapper.toItem
import com.example.com.inventorymanagement.domain.mappers.ItemMapper.toItemTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.math.BigDecimal
import kotlin.Int

class ItemRepository: IItemRepository {

    override suspend fun getAll(): List<Item> = newSuspendedTransaction(Dispatchers.IO) {
        ItemTable.selectAll().map { it.toItem() }
    }

    override suspend fun getById(itemId: Int): Item? = newSuspendedTransaction(Dispatchers.IO) {
        ItemTable.selectAll().where { ItemTable.itemId eq itemId}.map { it.toItem() }.singleOrNull()
    }

    override suspend fun create(item: Item): Item? = newSuspendedTransaction(Dispatchers.IO) {
        ItemTable.insert { it.toItemTable(item)}
        ItemTable.selectAll().where { ItemTable.itemName eq item.itemName }.map { it.toItem() }.singleOrNull()
    }

    override suspend fun update(item: Item): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val updatedRows = ItemTable.update({ ItemTable.itemId eq item.itemId}) { it.toItemTable(item)}
        updatedRows > 0
    }

    override suspend fun delete(itemId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        ItemTable.deleteWhere { ItemTable.itemId eq itemId } > 0
    }
}
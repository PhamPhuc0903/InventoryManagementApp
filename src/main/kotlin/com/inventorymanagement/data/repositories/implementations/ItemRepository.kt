package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.ItemTable
import com.example.com.inventorymanagement.data.models.Item
import com.example.com.inventorymanagement.data.repositories.interfaces.IItemRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.math.BigDecimal
import kotlin.Int

class ItemRepository: IItemRepository {
    private fun rowToItem(row: ResultRow): Item = Item(
        itemId = row[ItemTable.itemId],
        unit = row[ItemTable.unit],
        itemName = row[ItemTable.itemName],
        defaultPrice = row[ItemTable.defaultPrice]
    )

    override suspend fun getAll(): List<Item> = newSuspendedTransaction(Dispatchers.IO) {
        ItemTable.selectAll().map { rowToItem(it) }
    }

    override suspend fun getById(itemId: Int): Item? = newSuspendedTransaction(Dispatchers.IO) {
        ItemTable.selectAll().where { ItemTable.itemId eq itemId}.map { rowToItem(it) }.singleOrNull()
    }

    override suspend fun create(item: Item): Item? = newSuspendedTransaction(Dispatchers.IO) {
        val insertResult = ItemTable.insert {
            it[itemName] = item.itemName
            it[unit] = item.unit
            it[defaultPrice] = item.defaultPrice
        }
        ItemTable.selectAll().where { ItemTable.itemName eq item.itemName }.map { rowToItem(it) }.singleOrNull()
    }

    override suspend fun update(item: Item): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        val updatedRows = ItemTable.update({ ItemTable.itemId eq item.itemId}) {
            it[itemName] = item.itemName
            it[unit] = item.unit
            it[defaultPrice] = item.defaultPrice
        }
        updatedRows > 0
    }

    override suspend fun delete(itemId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        ItemTable.deleteWhere { ItemTable.itemId eq itemId } > 0
    }
}
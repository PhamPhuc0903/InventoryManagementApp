package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.SpecialPriceTable
import com.example.com.inventorymanagement.data.models.SpecialPrice
import com.example.com.inventorymanagement.data.repositories.interfaces.ISpecialPriceRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class SpecialPriceRepository : ISpecialPriceRepository {

    private fun rowToSpecialPrice(row: ResultRow): SpecialPrice = SpecialPrice(
        priceId = row[SpecialPriceTable.priceId],
        itemId = row[SpecialPriceTable.itemId],
        userId = row[SpecialPriceTable.userId],
        sellingPrice = row[SpecialPriceTable.sellingPrice],
        createdAt = row[SpecialPriceTable.createdAt]
    )

    override suspend fun getAll(): List<SpecialPrice> = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll().map { rowToSpecialPrice(it) }
    }

    override suspend fun getById(priceId: Int): SpecialPrice? = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where { SpecialPriceTable.priceId eq priceId }
            .map { rowToSpecialPrice(it) }
            .singleOrNull()
    }

    override suspend fun getByUserId(userId: UUID): List<SpecialPrice> = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where { SpecialPriceTable.userId eq userId }
            .map { rowToSpecialPrice(it) }
    }

    override suspend fun getByItemId(itemId: Int): List<SpecialPrice> = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where { SpecialPriceTable.itemId eq itemId }
            .map { rowToSpecialPrice(it) }
    }

    override suspend fun getByUserAndItem(userId: UUID, itemId: Int): SpecialPrice? = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where {
                (SpecialPriceTable.userId eq userId) and (SpecialPriceTable.itemId eq itemId)
            }
            .map { rowToSpecialPrice(it) }
            .singleOrNull()
    }

    override suspend fun create(specialPrice: SpecialPrice): SpecialPrice? = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.insert {
            it[itemId] = specialPrice.itemId
            it[userId] = specialPrice.userId
            it[sellingPrice] = specialPrice.sellingPrice
            it[createdAt] = specialPrice.createdAt
        }

        SpecialPriceTable.selectAll().where { (SpecialPriceTable.userId eq specialPrice.userId) and (SpecialPriceTable.itemId eq specialPrice.itemId) }.map { rowToSpecialPrice(it) }.singleOrNull()
    }

    override suspend fun update(specialPrice: SpecialPrice): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.update({ SpecialPriceTable.priceId eq specialPrice.priceId }) {
            it[itemId] = specialPrice.itemId
            it[userId] = specialPrice.userId
            it[sellingPrice] = specialPrice.sellingPrice
            it[createdAt] = specialPrice.createdAt
        } > 0
    }

    override suspend fun delete(priceId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.deleteWhere { SpecialPriceTable.priceId eq priceId } > 0
    }
}

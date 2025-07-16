package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.SpecialPriceTable
import com.example.com.inventorymanagement.data.models.SpecialPrice
import com.example.com.inventorymanagement.data.repositories.interfaces.ISpecialPriceRepository
import com.example.com.inventorymanagement.domain.mappers.SpecialPriceMapper.toSpecialPrice
import com.example.com.inventorymanagement.domain.mappers.SpecialPriceMapper.toSpecialPriceTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class SpecialPriceRepository : ISpecialPriceRepository {

    override suspend fun getAll(): List<SpecialPrice> = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll().map { it.toSpecialPrice() }
    }

    override suspend fun getById(priceId: Int): SpecialPrice? = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where { SpecialPriceTable.priceId eq priceId }
            .map { it.toSpecialPrice() }
            .singleOrNull()
    }

    override suspend fun getByUserId(userId: UUID): List<SpecialPrice> = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where { SpecialPriceTable.userId eq userId }
            .map { it.toSpecialPrice() }
    }

    override suspend fun getByItemId(itemId: Int): List<SpecialPrice> = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where { SpecialPriceTable.itemId eq itemId }
            .map { it.toSpecialPrice() }
    }

    override suspend fun getByUserAndItem(userId: UUID, itemId: Int): SpecialPrice? = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.selectAll()
            .where {
                (SpecialPriceTable.userId eq userId) and (SpecialPriceTable.itemId eq itemId)
            }
            .map { it.toSpecialPrice() }
            .singleOrNull()
    }

    override suspend fun create(specialPrice: SpecialPrice): SpecialPrice? = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.insert { it.toSpecialPriceTable(specialPrice)}

        SpecialPriceTable.selectAll().where { (SpecialPriceTable.userId eq specialPrice.userId) and (SpecialPriceTable.itemId eq specialPrice.itemId) }.map { it.toSpecialPrice() }.singleOrNull()
    }

    override suspend fun update(specialPrice: SpecialPrice): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.update({ SpecialPriceTable.priceId eq specialPrice.priceId }) { it.toSpecialPriceTable(specialPrice)} > 0
    }

    override suspend fun delete(priceId: Int): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        SpecialPriceTable.deleteWhere { SpecialPriceTable.priceId eq priceId } > 0
    }
}

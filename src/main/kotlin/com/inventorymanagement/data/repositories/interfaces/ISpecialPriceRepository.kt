package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.SpecialPrice
import java.util.UUID

interface ISpecialPriceRepository {
    suspend fun getAll(): List<SpecialPrice>
    suspend fun getById(priceId: Int): SpecialPrice?
    suspend fun getByUserId(userId: UUID): List<SpecialPrice>
    suspend fun getByItemId(itemId: Int): List<SpecialPrice>
    suspend fun getByUserAndItem(userId: UUID, itemId: Int): SpecialPrice?
    suspend fun create(specialPrice: SpecialPrice): SpecialPrice?
    suspend fun update(specialPrice: SpecialPrice): Boolean
    suspend fun delete(priceId: Int): Boolean
}

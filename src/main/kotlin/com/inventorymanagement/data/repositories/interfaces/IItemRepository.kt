package com.example.com.inventorymanagement.data.repositories.interfaces

import com.example.com.inventorymanagement.data.models.Item

interface IItemRepository {
    suspend fun getAll(): List<Item>
    suspend fun getById(itemId: Int): Item?
    suspend fun create(item: Item): Item?
    suspend fun update(item: Item): Boolean
    suspend fun delete(itemId: Int): Boolean
}
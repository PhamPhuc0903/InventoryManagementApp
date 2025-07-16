package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.ItemTable
import com.example.com.inventorymanagement.data.models.Item
import com.example.com.inventorymanagement.domain.dto.request.item.CreateItemRequest
import com.example.com.inventorymanagement.domain.dto.request.item.UpdateItemRequest
import com.example.com.inventorymanagement.domain.dto.response.item.ItemListResponse
import com.example.com.inventorymanagement.domain.dto.response.item.ItemResponse
import com.example.com.inventorymanagement.domain.dto.response.item.ItemWithEffectivePriceListResponse
import com.example.com.inventorymanagement.domain.dto.response.item.ItemWithEffectivePriceResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.time.LocalDateTime
import java.util.UUID

object ItemMapper {
    fun toItem(request: CreateItemRequest): Item = Item(
        itemId = 0,
        itemName = request.itemName,
        unit = request.unit,
        defaultPrice = request.defaultPrice
    )

    fun toItem(request: UpdateItemRequest, existingItem: Item): Item = existingItem.copy(
        itemName = request.itemName?: existingItem.itemName,
        unit = request.unit?: existingItem.unit,
        defaultPrice = request.defaultPrice?: existingItem.defaultPrice
    )

    fun Item.toItemResponse(): ItemResponse = ItemResponse(
        itemId = this.itemId,
        itemName = this.itemName,
        unit = this.unit,
        defaultPrice = this.defaultPrice
    )

    fun toItemListResponse(items: List<Item>, total: Long): ItemListResponse = ItemListResponse(
        items = items.map { it.toItemResponse() },
        total = total
    )

    fun Item.toItemWithEffectivePriceResponse(priceId: Int?, userId: UUID?, createdAt: LocalDateTime?): ItemWithEffectivePriceResponse =
        ItemWithEffectivePriceResponse(
            itemId = this.itemId,
            itemName = this.itemName,
            unit = this.unit,
            effectivePrice = this.defaultPrice,
            priceId = priceId,
            userId = userId,
            createdAt = createdAt
        )

    fun toItemWithEffectivePriceListResponse(items: List<ItemWithEffectivePriceResponse>, total: Long): ItemWithEffectivePriceListResponse =
        ItemWithEffectivePriceListResponse(
            items = items,
            total = total
        )

    fun ResultRow.toItem(): Item = Item(
        itemId = this[ItemTable.itemId],
        itemName = this[ItemTable.itemName],
        unit = this[ItemTable.unit],
        defaultPrice = this[ItemTable.defaultPrice]
    )

    fun InsertStatement<Number>.toItemTable(item: Item) {
        this[ItemTable.itemName] = item.itemName
        this[ItemTable.unit] = item.unit
        this[ItemTable.defaultPrice] = item.defaultPrice
    }

    fun UpdateStatement.toItemTable(item: Item) {
        this[ItemTable.itemName] = item.itemName
        this[ItemTable.unit] = item.unit
        this[ItemTable.defaultPrice] = item.defaultPrice
    }
}
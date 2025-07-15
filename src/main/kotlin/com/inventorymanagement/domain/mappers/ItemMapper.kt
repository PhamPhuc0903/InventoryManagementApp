package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.models.Item
import com.example.com.inventorymanagement.data.models.SpecialPrice
import com.example.com.inventorymanagement.domain.dto.request.item.CreateItemRequest
import com.example.com.inventorymanagement.domain.dto.request.item.CreateSpecialPriceRequest
import com.example.com.inventorymanagement.domain.dto.request.item.UpdateItemRequest
import java.time.LocalDateTime

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

    fun toSpecialPrice(request: CreateSpecialPriceRequest): SpecialPrice = SpecialPrice(
        priceId = 0,
        itemId = request.itemId,
        userId = request.userId,
        sellingPrice = request.sellingPrice,
        createdAt = LocalDateTime.now()
    )


}
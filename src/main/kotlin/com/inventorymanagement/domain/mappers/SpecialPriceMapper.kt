package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.SpecialPriceTable
import com.example.com.inventorymanagement.data.models.SpecialPrice
import com.example.com.inventorymanagement.domain.dto.request.item.CreateSpecialPriceRequest
import com.example.com.inventorymanagement.domain.dto.response.item.SpecialPriceResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.time.LocalDateTime

object SpecialPriceMapper {
    fun toSpecialPrice(request: CreateSpecialPriceRequest): SpecialPrice = SpecialPrice(
        priceId = 0,
        itemId = request.itemId,
        userId = request.userId,
        sellingPrice = request.sellingPrice,
        createdAt = LocalDateTime.now()
    )

    fun SpecialPrice.toSpecialPriceResponse(): SpecialPriceResponse = SpecialPriceResponse(
        priceId = this.priceId,
        itemId = this.itemId,
        userId = this.userId,
        sellingPrice = this.sellingPrice,
        createdAt = this.createdAt
    )

    fun ResultRow.toSpecialPrice(): SpecialPrice = SpecialPrice(
        priceId = this[SpecialPriceTable.priceId],
        itemId = this[SpecialPriceTable.itemId],
        userId = this[SpecialPriceTable.userId],
        sellingPrice = this[SpecialPriceTable.sellingPrice],
        createdAt = this[SpecialPriceTable.createdAt]
    )

    fun InsertStatement<Number>.toSpecialPriceTable(specialPrice: SpecialPrice) {
        this[SpecialPriceTable.itemId] = specialPrice.itemId
        this[SpecialPriceTable.userId] = specialPrice.userId
        this[SpecialPriceTable.sellingPrice] = specialPrice.sellingPrice
        this[SpecialPriceTable.createdAt] = specialPrice.createdAt
    }

    fun UpdateStatement.toSpecialPriceTable(specialPrice: SpecialPrice) {
        this[SpecialPriceTable.itemId] = specialPrice.itemId
        this[SpecialPriceTable.userId] = specialPrice.userId
        this[SpecialPriceTable.sellingPrice] = specialPrice.sellingPrice
        this[SpecialPriceTable.createdAt] = specialPrice.createdAt
    }
}
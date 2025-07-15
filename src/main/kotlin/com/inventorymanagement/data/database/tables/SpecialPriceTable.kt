package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.math.BigDecimal
import java.time.LocalDateTime

object SpecialPriceTable: Table("special_prices") {
    val priceId = integer("price_id").autoIncrement()
    val itemId = reference("item_id", ItemTable.itemId)
    val userId = reference("user_id", UserTable.userId)
    val sellingPrice = decimal("selling_price",10,2).default(BigDecimal.ZERO)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }


}
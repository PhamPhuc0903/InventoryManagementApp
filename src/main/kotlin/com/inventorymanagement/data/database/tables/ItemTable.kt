package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

object ItemTable: Table("items") {
    val itemId = integer("item_id").autoIncrement()
    val itemName = varchar("item_name",50).uniqueIndex()

    val unit = varchar("unit",50)
    val defaultPrice = decimal("default_price",18,2).default(BigDecimal.ZERO)
    override val primaryKey = PrimaryKey(itemId)
}
package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table

object AdminUserTable: Table("admin_users") {
    val adminId = reference("admin_id", UserTable.userId)
    val userId = reference("user_id", UserTable.userId)
    override val primaryKey = PrimaryKey(adminId,userId)
}
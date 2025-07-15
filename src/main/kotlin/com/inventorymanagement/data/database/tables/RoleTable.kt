package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table

object RoleTable: Table("roles") {
    val roleName = varchar("role_name",50).uniqueIndex()
    val roleDescription = varchar("role_description",255).nullable()
    override val primaryKey = PrimaryKey(roleName)
}
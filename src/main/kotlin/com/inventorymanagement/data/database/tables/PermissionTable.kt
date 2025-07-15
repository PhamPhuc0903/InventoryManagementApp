package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table

object PermissionTable: Table("permissions") {
    val permissionName = varchar("permission_name",50).uniqueIndex()
    val permissionDescription = varchar("permission_description",255).nullable()
    override val primaryKey = PrimaryKey(permissionName)
}
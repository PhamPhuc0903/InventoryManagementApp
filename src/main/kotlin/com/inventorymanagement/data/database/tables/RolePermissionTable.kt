package com.example.com.inventorymanagement.data.database.tables

import org.jetbrains.exposed.sql.Table

object RolePermissionTable: Table("roles_permissions") {
    val roleName = reference("role_name", RoleTable.roleName)
    val permissionName = reference("permission_name", PermissionTable.permissionName)
    override val primaryKey = PrimaryKey(roleName,permissionName)
}
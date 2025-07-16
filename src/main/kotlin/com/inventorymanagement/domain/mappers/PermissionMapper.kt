package com.example.com.inventorymanagement.domain.mappers

import com.example.com.inventorymanagement.data.database.tables.PermissionTable
import com.example.com.inventorymanagement.data.models.Permission
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

object PermissionMapper {
    fun ResultRow.toPermission(): Permission = Permission(
        permissionName = this[PermissionTable.permissionName],
        permissionDescription = this[PermissionTable.permissionDescription]
    )

    fun InsertStatement<Number>.toPermissionTable(permission: Permission) {
        this[PermissionTable.permissionName] = permission.permissionName
        this[PermissionTable.permissionDescription] = permission.permissionDescription
    }

    fun UpdateStatement.toPermissionTable(permission: Permission) {
        this[PermissionTable.permissionDescription] = permission.permissionDescription
    }
}
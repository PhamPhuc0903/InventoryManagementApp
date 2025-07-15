package com.example.com.inventorymanagement.data.repositories.implementations

import com.example.com.inventorymanagement.data.database.tables.PermissionTable
import com.example.com.inventorymanagement.data.models.Permission
import com.example.com.inventorymanagement.data.repositories.interfaces.IPermissionRepository
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class PermissionRepository : IPermissionRepository {

    private fun rowToPermission(row: ResultRow): Permission = Permission(
        permissionName = row[PermissionTable.permissionName],
        permissionDescription = row[PermissionTable.permissionDescription]
    )

    override suspend fun getAll(): List<Permission> = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.selectAll().map { rowToPermission(it) }
    }

    override suspend fun getByName(permissionName: String): Permission? = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable
            .selectAll()
            .where { PermissionTable.permissionName eq permissionName }
            .map { rowToPermission(it) }
            .singleOrNull()
    }

    override suspend fun create(permission: Permission): Permission? = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.insert {
            it[permissionName] = permission.permissionName
            it[permissionDescription] = permission.permissionDescription
        }

        PermissionTable
            .selectAll()
            .where { PermissionTable.permissionName eq permission.permissionName }
            .map { rowToPermission(it) }
            .singleOrNull()
    }

    override suspend fun update(permission: Permission): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.update({
            PermissionTable.permissionName eq permission.permissionName
        }) {
            it[permissionDescription] = permission.permissionDescription
        } > 0
    }

    override suspend fun delete(permissionName: String): Boolean = newSuspendedTransaction(Dispatchers.IO) {
        PermissionTable.deleteWhere {
            PermissionTable.permissionName eq permissionName
        } > 0
    }
}
